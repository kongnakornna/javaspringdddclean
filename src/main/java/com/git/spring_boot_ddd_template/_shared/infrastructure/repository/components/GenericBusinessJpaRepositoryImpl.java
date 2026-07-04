package com.git.spring_boot_ddd_template._shared.infrastructure.repository.components;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.git.spring_boot_ddd_template._shared.infrastructure.GenericBusinessEntity;
import com.git.spring_boot_ddd_template.exception.models.InfrastructureException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


public abstract class GenericBusinessJpaRepositoryImpl<S extends GenericBusinessEntity> {

    private static final Logger logger = LoggerFactory.getLogger(GenericBusinessJpaRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    private Predicate buildCompanyAndNotDeletedPredicate(CriteriaBuilder cb, Root<S> root, UUID companyId) {
        return cb.and(
                cb.equal(root.get("whitelabelId"), companyId),
                cb.isFalse(root.get("deleted"))
        );
    }

    private Predicate buildUserCompanyAndNotDeletedPredicate(CriteriaBuilder cb, Root<S> root, UUID userId, UUID companyId) {
        return cb.and(
                cb.equal(root.get("userId"), userId),
                cb.equal(root.get("whitelabelId"), companyId),
                cb.isFalse(root.get("deleted"))
        );
    }

    /**
     * Finds an entity by its id, company id.
     *
     * @param id          The id of the entity.
     * @param companyId   The id of the company.
     * @param entityClass The class of the entity.
     * @return An optional of the entity.
     */
    public Optional<S> findById(UUID id, UUID companyId, Class<S> entityClass) throws InfrastructureException {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<S> query = cb.createQuery(entityClass);
            Root<S> root = query.from(entityClass);

            Predicate companyAndNotDeletedPredicate = buildCompanyAndNotDeletedPredicate(cb, root, companyId);
            Predicate idPredicate = cb.equal(root.get("id"), id);

            query.select(root).where(cb.and(companyAndNotDeletedPredicate, idPredicate));

            return entityManager.createQuery(query).getResultStream().findFirst();
        } catch (Exception e) {
            logger.error("Failed to retrieve entity with id: {}", id, e);
            throw new InfrastructureException("Error while trying to find entity by id.", e);
        }
    }

    /**
     * Finds all entities by user id, company id and entity class.
     *
     * @param userId      The id of the user.
     * @param companyId   The id of the company.
     * @param entityClass The class of the entity.
     * @return A list of entities.
     */
    public List<S> findAll(UUID userId, UUID companyId, Class<S> entityClass) throws InfrastructureException {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<S> query = cb.createQuery(entityClass);
            Root<S> root = query.from(entityClass);

            Predicate userCompanyAndNotDeleted = buildUserCompanyAndNotDeletedPredicate(cb, root, userId, companyId);

            query.select(root).where(userCompanyAndNotDeleted);

            return entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            logger.error("Failed to retrieve entities", e);
            throw new InfrastructureException("Error while trying to find all entities by id.", e);
        }
    }

    /**
     * Finds all entities by a list of ids, also filtered by user id, company id.
     *
     * @param userId      The id of the user.
     * @param companyId   The id of the company.
     * @param ids         The list of ids.
     * @param entityClass The class of the entity.
     * @return A list of entities.
     */
    public List<S> findAllByIds(UUID userId, UUID companyId, Set<UUID> ids, Class<S> entityClass)
            throws InfrastructureException {
        try {
            if (ids == null || ids.isEmpty()) {
                return List.of();
            }

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<S> query = cb.createQuery(entityClass);
            Root<S> root = query.from(entityClass);

            Predicate userCompanyAndNotDeleted = buildUserCompanyAndNotDeletedPredicate(cb, root, userId, companyId);
            Predicate idsPredicate = root.get("id").in(ids);

            query.select(root).where(cb.and(userCompanyAndNotDeleted, idsPredicate));

            return entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            logger.error("Failed to retrieve entities by ids", e);
            throw new InfrastructureException("Error while trying to find all entities by ids.", e);
        }
    }

    /**
     * Finds all entities by user id, company id and entity class. Paginated.
     *
     * @param userId      The id of the user.
     * @param companyId   The id of the company.
     * @param pageable    The page request.
     * @param entityClass The class of the entity.
     * @return A page of entities.
     */
    public Page<S> findAllPaginated(UUID userId,
                                    UUID companyId,
                                    PageRequest pageable,
                                    Class<S> entityClass) throws InfrastructureException {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();

            CriteriaQuery<S> query = cb.createQuery(entityClass);
            Root<S> root = query.from(entityClass);

            Predicate userCompanyAndNotDeleted = buildUserCompanyAndNotDeletedPredicate(cb, root, userId, companyId);
            query.where(userCompanyAndNotDeleted);

            List<Order> orders = pageable.getSort()
                    .stream()
                    .map(sortOrder ->
                            sortOrder.isAscending()
                                    ? cb.asc(root.get(sortOrder.getProperty()))
                                    : cb.desc(root.get(sortOrder.getProperty()))
                    ).collect(Collectors.toList());
            query.orderBy(orders);

            CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
            Root<S> countRoot = countQuery.from(entityClass);
            countQuery.select(cb.count(countRoot));
            countQuery.where(buildUserCompanyAndNotDeletedPredicate(cb, countRoot, userId, companyId));

            Long totalCount = entityManager.createQuery(countQuery).getSingleResult();

            List<S> content = entityManager.createQuery(query)
                    .setFirstResult((int) pageable.getOffset())
                    .setMaxResults(pageable.getPageSize())
                    .getResultList();

            return new PageImpl<>(content, pageable, totalCount);
        } catch (Exception e) {
            logger.error("Failed to retrieve entities (paginated)", e);
            throw new InfrastructureException("Error while trying to find all entities by id (paginated).", e);
        }
    }
}
