package com.git.spring_boot_ddd_template._shared.infrastructure.repository.exception;

import com.git.spring_boot_ddd_template.exception.models.InfrastructureException;

public class EntityNotFound extends InfrastructureException {
    public EntityNotFound(String message, Throwable e) {
        super(message, e);
    }
}
