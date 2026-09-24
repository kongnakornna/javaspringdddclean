package com.icmon._shared.infrastructure.mapper;

import com.icmon.exception.models.InfrastructureException;

public class EntityNotFound extends InfrastructureException {
    public EntityNotFound(String message, Throwable e) {
        super(message, e);
    }
}
