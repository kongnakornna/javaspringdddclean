package com.icmon._shared.infrastructure.repository.interfaces;

import jakarta.annotation.Nonnull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.icmon._shared.domain.GenericClass;
import com.icmon._shared.infrastructure.RepositoryAuth;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.InfrastructureException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Every repository in this system should use this Generic interface
 * for handling data in the database. This one is used for entities that
 * gets saved on a relational database, that being Postgres.
 *
 * @author Lucas Batista Pereira
 * @version v1.0
 * @since v1.0 (30/11/2024)
 */
public interface GenericBusinessRepository<E extends GenericClass> {

    E create(@Nonnull E entity, @Nonnull RepositoryAuth auth) throws SystemGlobalException;

    E read(@Nonnull UUID id, @Nonnull RepositoryAuth auth) throws SystemGlobalException;

    E update(@Nonnull E entity, @Nonnull RepositoryAuth auth) throws SystemGlobalException;

    void delete(@Nonnull UUID id, @Nonnull RepositoryAuth auth) throws SystemGlobalException;

    Optional<E> findById(@Nonnull UUID id, @Nonnull RepositoryAuth auth) throws InfrastructureException;

    Optional<List<E>> findAll(@Nonnull RepositoryAuth auth) throws SystemGlobalException;

    Page<E> findAllPaginated(@Nonnull PageRequest pageRequest, @Nonnull RepositoryAuth auth) throws SystemGlobalException;

    Optional<List<E>> findAllByIds(@Nonnull List<UUID> ids, @Nonnull RepositoryAuth auth) throws SystemGlobalException;

    void check(@Nonnull UUID id, @Nonnull RepositoryAuth auth) throws SystemGlobalException;

    void checkAll(@Nonnull Set<UUID> ids, @Nonnull RepositoryAuth auth) throws SystemGlobalException;

    CompletableFuture<E> createAsync(@Nonnull E entity, @Nonnull RepositoryAuth auth) throws SystemGlobalException;

    CompletableFuture<E> updateAsync(@Nonnull E entity, @Nonnull RepositoryAuth auth) throws SystemGlobalException;

    void deleteAsync(@Nonnull UUID id, @Nonnull RepositoryAuth auth) throws SystemGlobalException;
}