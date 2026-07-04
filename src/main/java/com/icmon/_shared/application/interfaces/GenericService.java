package com.icmon._shared.application.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.icmon._shared.domain.GenericClass;
import com.icmon.exception.SystemGlobalException;

public interface GenericService<E extends GenericClass> {
    E read(UUID id) throws SystemGlobalException;

    Optional<E> findById(UUID id) throws SystemGlobalException;

    Optional<List<E>> readAll() throws SystemGlobalException;

    Optional<List<E>> readAllByIds(List<UUID> ids) throws SystemGlobalException;

    void check(UUID id) throws SystemGlobalException;

    void checkAll(Set<UUID> ids) throws SystemGlobalException;
}
