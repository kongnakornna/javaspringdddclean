package com.git.spring_boot_ddd_template._shared.application;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.git.spring_boot_ddd_template._shared.application.interfaces.GenericService;
import com.git.spring_boot_ddd_template._shared.domain.GenericBusinessClass;
import com.git.spring_boot_ddd_template._shared.infrastructure.repository.interfaces.GenericBusinessRepository;
import com.git.spring_boot_ddd_template.exception.SystemGlobalException;
import com.git.spring_boot_ddd_template.exception.models.ApplicationException;
import com.git.spring_boot_ddd_template.utils.converter.ObjectMerger;

public abstract class GenericServiceImpl
        <E extends GenericBusinessClass, R extends GenericBusinessRepository<E>>
        extends GenericAuthDomainServiceImpl
        implements GenericService<E> {
    protected final R repository;

    protected GenericServiceImpl(R repository1) {
        this.repository = repository1;
    }

    /*
    ------------------- Public Read Operations -------------------
    */

    public E read(UUID id) throws SystemGlobalException {
        try {
            return this.repository.read(id, getRepositoryAuth());
        } catch (SystemGlobalException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Something went wrong reading an entity.", e);
        }
    }

    public Optional<E> findById(UUID id) throws SystemGlobalException {
        try {
            return this.repository.findById(id, getRepositoryAuth());
        } catch (SystemGlobalException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Something went wrong finding an entity.", e);
        }
    }

    public Optional<List<E>> readAll() throws SystemGlobalException {
        try {
            return this.repository.findAll(getRepositoryAuth());
        } catch (SystemGlobalException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Something went wrong reading all entities.", e);
        }
    }

    public Optional<List<E>> readAllByIds(List<UUID> ids) throws SystemGlobalException {
        try {
            return this.repository.findAllByIds(ids, getRepositoryAuth());
        } catch (SystemGlobalException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Something went wrong reading all entities by id.", e);
        }
    }

    public void check(UUID id) throws SystemGlobalException {
        try {
            this.repository.check(id, getRepositoryAuth());
        } catch (SystemGlobalException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Something went wrong checking an entity.", e);
        }
    }

    public void checkAll(Set<UUID> ids) throws SystemGlobalException {
        try {
            this.repository.checkAll(ids, getRepositoryAuth());
        } catch (SystemGlobalException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Something went wrong checking all entities by ids.", e);
        }
    }

    /*

        ------------------- Protected Write Operations -------------------

     */

    protected E create(E entity) throws SystemGlobalException {
        try {
            return this.repository.create(entity, getRepositoryAuth());
        } catch (SystemGlobalException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Something went wrong creating an entity.", e);
        }
    }

    protected <D> E update(UUID id, D data) throws SystemGlobalException {
        try {
            E entity = this.read(id);
            entity = ObjectMerger.mergeObjects(data, entity);
            return this.repository.update(entity, getRepositoryAuth());
        } catch (SystemGlobalException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Something went wrong updating an entity.", e);
        }
    }

    protected void delete(UUID id) throws SystemGlobalException {
        try {
            this.repository.delete(id, getRepositoryAuth());
        } catch (SystemGlobalException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Something went wrong deleting an entity.", e);
        }
    }

    protected <D> CompletableFuture<E> updateAsync(UUID id, D data) throws SystemGlobalException {
        try {
            E entity = this.read(id);
            entity = ObjectMerger.mergeObjects(data, entity);
            return this.repository.updateAsync(entity, getRepositoryAuth());
        } catch (SystemGlobalException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Something went wrong async updating an entity.", e);
        }
    }

    protected void deleteAsync(UUID id) throws SystemGlobalException {
        try {
            this.repository.deleteAsync(id, getRepositoryAuth());
        } catch (SystemGlobalException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("Something went wrong async deleting an entity.", e);
        }
    }
}