package com.icmon._shared.infrastructure.repository;

import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.scheduling.annotation.Async;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon._shared.infrastructure.GenericBusinessEntity;
import com.icmon._shared.infrastructure.RepositoryAuth;
import com.icmon._shared.infrastructure.mapper.GenericBusinessMapper;
import com.icmon._shared.infrastructure.repository.components.GenericBusinessJpaRepositoryImpl;
import com.icmon._shared.infrastructure.repository.exception.EntityNotFound;
import com.icmon._shared.infrastructure.repository.interfaces.GenericBusinessRepository;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.InfrastructureException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public abstract class GenericBusinessRepositoryImpl
        <E extends GenericBusinessClass, S extends GenericBusinessEntity>
        extends GenericBusinessJpaRepositoryImpl<S>
        implements GenericBusinessRepository<E> {

    private static final Logger logger = LoggerFactory.getLogger(GenericBusinessRepositoryImpl.class);
    private final GenericBusinessMapper<E, S> mapper;
    private final SimpleJpaRepository<S, UUID> jpaRepository;
    private final Class<S> entityClass;


    /**
     * Every domain repository should have a super constructor for this class.
     *
     * @param mapper        GenericMapper for any domain class.
     * @param jpaRepository SimpleJpaRepository class for general use.
     * @param entityClass   Reference class for infra operations.
     */
    @Autowired
    public GenericBusinessRepositoryImpl(
            GenericBusinessMapper<E, S> mapper,
            SimpleJpaRepository<S, UUID> jpaRepository,
            Class<S> entityClass
    ) {
        this.mapper = mapper;
        this.jpaRepository = jpaRepository;
        this.entityClass = entityClass;
    }

   
    private void setCommonFields(
            S schema,
            RepositoryAuth auth) {
        schema.setUserId(auth.userId());
        schema.setWhitelabelId(auth.whitelabelId());
    }

    /*
            --------------     Sync Operations     --------------
     */

    /**
     * Creates an entity in database
     *
     * @param entity Entity to be crated.
     * @param auth Repository credentials, must contain user and WhitelabelId.
     * @return E
     * @throws InfrastructureException Custom error.
     */
    @Override
    @Transactional
    public E create(
            @Nonnull E entity,
            @Nonnull RepositoryAuth auth) throws InfrastructureException {
        try {
            S schema = mapper.toSchemaForCreate(entity);
            setCommonFields(schema, auth);
            S savedSchema = jpaRepository.save(schema);
            logger.info("A new {} was created in the system!", entity.getClass().getSimpleName());
            return mapper.toEntity(savedSchema);
        } catch (Exception e) {
            logger.error("Failed to save entity: {}", entity, e);
            throw new InfrastructureException("Failed to save entity", e);
        }
    }


    /**
     * Reads and returns an entity by its id.
     *
     * @param id EntityId to be read.
     * @param auth Repository credentials, must contain user and WhitelabelId.
     * @return E
     * @throws InfrastructureException Custom error.
     */
    @Override
    public E read(
            @Nonnull UUID id,
            @Nonnull RepositoryAuth auth) throws InfrastructureException {
        try {
            return mapper.toEntity(this.readInternal(id, auth));
        } catch (SystemGlobalException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Failed to retrieve entity with id: {}", id, e);
            throw new InfrastructureException("Failed to retrieve data", e);
        }
    }


    /**
     * Used for specifics situations in this class.
     *
     * @param id EntityId to be read.
     * @param auth Repository credentials, must contain user and WhitelabelId.
     * @return E
     * @throws InfrastructureException Custom error.
     */
    protected S readInternal(
            UUID id,
            RepositoryAuth auth) throws InfrastructureException {
        try {
            return this.findById(id, auth.whitelabelId(), entityClass).orElseThrow(() ->
                            new EntityNotFound("Entity " + entityClass.getSimpleName() + " not found with id: " + id, null));
        } catch (SystemGlobalException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Failed to retrieve entity with id: {}", id, e);
            throw new InfrastructureException("Failed to retrieve data", e);
        }
    }


    /**
     * Updates an entity.
     *
     * @param entity Entity to be updated.
     * @param auth Repository credentials, must contain user and WhitelabelId.
     * @return E
     * @throws InfrastructureException Custom error.
     */
    @Override
    @Transactional
    public E update(
            @Nonnull E entity,
            @Nonnull RepositoryAuth auth) throws InfrastructureException {
        try {
            S existingSchema = this.readInternal(entity.getId(), auth);
            mapper.updateEntityFromDomain(existingSchema, entity);
            existingSchema.setUpdatedAt(LocalDateTime.now());
            S savedSchema = jpaRepository.save(existingSchema);
            logger.info("An existing entity of class {} is being updated in the system!",
                    entity.getClass().getSimpleName());
            return mapper.toEntity(savedSchema);
        } catch (Exception e) {
            logger.error("Failed to update entity: {}", entity, e);
            throw new InfrastructureException("Failed to update entity", e);
        }
    }

    /**
     * Sync version of delete method.
     *
     * @param id Reference for entity to be deleted.
     * @throws InfrastructureException Thrown when an unexpected database error occurs.
     * @author Lucas Batista Pereira
     * @since v1.0 (06/11/2024)
     */
    @Override
    @Transactional
    public void delete(
            @Nonnull UUID id,
            @Nonnull RepositoryAuth auth) throws InfrastructureException {
        try {
            this.deleteInternal(id, auth);
        } catch (Exception e) {
            logger.error("Failed to delete entity with id: {}", id, e);
            throw new InfrastructureException("Failed to delete entity", e);
        }
    }

    protected void deleteInternal(
            UUID id,
            RepositoryAuth auth) throws InfrastructureException {
        S schema = this.readInternal(id, auth);

        schema.setDeleted(true);
        schema.setDeletedAt(LocalDateTime.now());
        jpaRepository.save(schema);

        logger.info("Entity with id: {} was marked as deleted.", id);
    }

    @Override
    public Optional<E> findById(
            @Nonnull UUID id,
            @Nonnull RepositoryAuth auth) {
        try {
            E entity = mapper.toEntity(this.readInternal(id, auth));
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            logger.error("Failed to retrieve entity with id: {}", id, e);
            return Optional.empty();
        }
    }

    /**
     * Find all entities of given entity class.
     *
     * @return Optional<List < E>>
     * @throws InfrastructureException Thrown when an unexpected database error occurs.
     * @author Lucas Batista Pereira
     * @since v1.0 (06/11/2024)
     */
    @Override
    public Optional<List<E>> findAll(
            @Nonnull RepositoryAuth auth) throws InfrastructureException {
        try {
            List<S> schemas = this.findAll(auth.userId(), auth.whitelabelId(), entityClass);
            List<E> entities = schemas.stream()
                    .map(mapper::toEntity)
                    .collect(Collectors.toList());
            return entities.isEmpty() ? Optional.empty() : Optional.of(entities);
        } catch (Exception e) {
            logger.error("Failed to find all entities for userId: {} and whitelabelId: {}",
                    auth.userId(), auth.whitelabelId(), e);
            throw new InfrastructureException("Failed to find all entities", e);
        }
    }

    /**
     * Find all entities by given ids.
     *
     * @param ids Reference ids for reading.
     * @return Optional<List < E>>
     * @throws InfrastructureException Thrown when an unexpected database error occurs.
     * @author Lucas Batista Pereira
     * @since v1.0 (06/11/2024)
     */
    @Override
    public Optional<List<E>> findAllByIds(
            @Nonnull List<UUID> ids,
            @Nonnull RepositoryAuth auth) throws InfrastructureException {
        try {
            Set<UUID> idSet = new HashSet<>(ids);
            List<S> schemas = this.findAllByIds(auth.userId(), auth.whitelabelId(), idSet, entityClass);
            List<E> entities = schemas.stream()
                    .map(mapper::toEntity)
                    .collect(Collectors.toList());
            return entities.isEmpty() ? Optional.empty() : Optional.of(entities);
        } catch (Exception e) {
            logger.error("Failed to find entities by IDs: {}", ids, e);
            throw new InfrastructureException("Failed to find entities by IDs", e);
        }
    }

    /**
     * Check the existence of an entity given its id.
     *
     * @param id Reference id for checking.
     * @throws InfrastructureException Thrown when an unexpected database error occurs.
     * @author Lucas Batista Pereira
     * @since v1.0 (06/11/2024)
     */
    @Override
    @Transactional
    public void check(
            @Nonnull UUID id,
            @Nonnull RepositoryAuth auth) throws InfrastructureException {
        try {
            this.readInternal(id, auth);
        } catch (Exception e) {
            logger.error("Failed to check entity by ID: {}", id, e);
            throw new InfrastructureException("Failed to find entities by IDs", e);
        }
    }

    /**
     * Check the existence of all entities given its ids.
     *
     * @param ids Reference ids for checking.
     * @throws InfrastructureException Thrown when an unexpected database error occurs.
     * @author Lucas Batista Pereira
     * @since v1.0 (06/11/2024)
     */
    @Override
    @Transactional
    public void checkAll(
            @Nonnull Set<UUID> ids,
            @Nonnull RepositoryAuth auth) throws InfrastructureException {
        try {
        List<S> schemas = this.findAllByIds(auth.userId(), auth.whitelabelId(), ids, entityClass);
        if (schemas.size() != ids.size()) {
            Set<UUID> foundIds = schemas.stream().map(S::getId).collect(Collectors.toSet());
            Set<UUID> missingIds = ids.stream().filter(id -> !foundIds.contains(id)).collect(Collectors.toSet());
            throw new EntityNotFound
                    ("Entities not found or already deleted for IDs: " + missingIds, null);
        }
        } catch (Exception e) {
            logger.error("Failed to check entities by IDd: {}", ids, e);
            throw new InfrastructureException("Failed to find entities by IDs", e);
        }
    }

    /**
     * Find all entities paginated.
     *
     * @param pageRequest PageRequest object.
     * @return Page<E>
     * @throws InfrastructureException Thrown when an unexpected database error occurs.
     * @since v1.0 (06/11/2024)
     */
    public Page<E> findAllPaginated(
            @Nonnull PageRequest pageRequest,
            @Nonnull RepositoryAuth auth) throws InfrastructureException {
        try {
            return this.findAllPaginated(auth.userId(), auth.whitelabelId(), pageRequest, entityClass)
                    .map(mapper::toEntity);
        } catch (Exception e) {
            logger.error("Failed to find entities paginated");
            throw new InfrastructureException("Failed to find entities by IDs", e);
        }
    }

    /*
            --------------     Async Operations     --------------
     */

    /**
     * In the future, creation will be done on a remote database using
     * kafka queues, that's why it needs to be async.
     *
     * @param entity Entity that extends GenericBusinessClass
     * @return CompletableFuture<E>
     * @throws InfrastructureException Thrown when an unexpected database error occurs.
     * @author Lucas Batista Pereira
     * @since v1.0 (06/11/2024)
     */
    @Override
    @Async
    @Transactional
    public CompletableFuture<E> createAsync(
            @Nonnull E entity,
            @Nonnull RepositoryAuth auth) throws InfrastructureException {
        try {
            S schema = mapper.toSchemaForCreate(entity);
            setCommonFields(schema, auth);
            S savedSchema = jpaRepository.save(schema);
            logger.info("A new {} was created in the system! (Async)", entity.getClass().getSimpleName());
            return CompletableFuture.completedFuture(mapper.toEntity(savedSchema));
        } catch (Exception e) {
            logger.error("Failed to save entity: {}", entity, e);
            throw new InfrastructureException("Failed to async save entity", e);
        }
    }

    /**
     * Update a schema based on an entity.
     *
     * @param entity Entity to be updated.
     * @return CompletableFuture<E>
     * @throws InfrastructureException Thrown when an unexpected database error occurs.
     * @author Lucas Batista Pereira
     * @since v1.0 (06/11/2024)
     */
    @Override
    @Async
    @Transactional
    public CompletableFuture<E> updateAsync(
            @Nonnull E entity,
            @Nonnull RepositoryAuth auth) throws InfrastructureException {
        try {
            S existingSchema = this.readInternal(entity.getId(), auth);
            mapper.updateEntityFromDomain(existingSchema, entity);
            existingSchema.setUpdatedAt(LocalDateTime.now());
            S savedSchema = jpaRepository.save(existingSchema);
            logger.info("An existing entity of class {} is being updated in the system! (Async)",
                    entity.getClass().getSimpleName());
            return CompletableFuture.completedFuture(mapper.toEntity(savedSchema));
        } catch (Exception e) {
            logger.error("Failed to update entity: {}", entity, e);
            throw new InfrastructureException("Failed to async update entity", e);
        }
    }

    /**
     * Soft delete an entity.
     *
     * @param id Reference for entity to be deleted.
     * @throws InfrastructureException Thrown when an unexpected database error occurs.
     * @author Lucas Batista Pereira
     * @since v1.0 (06/11/2024)
     */
    @Override
    @Async
    @Transactional
    public void deleteAsync(
            @Nonnull UUID id,
            @Nonnull RepositoryAuth auth) throws InfrastructureException {
        try {
            delete(id, auth);
        } catch (Exception e) {
            logger.error("Failed to delete entity with id: {}", id, e);
            throw new InfrastructureException("Failed to async delete entity", e);
        }
    }
}