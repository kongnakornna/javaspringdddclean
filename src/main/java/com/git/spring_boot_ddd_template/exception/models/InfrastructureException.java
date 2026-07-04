package com.git.spring_boot_ddd_template.exception.models;

import com.git.spring_boot_ddd_template.exception.SystemGlobalException;


public class InfrastructureException extends SystemGlobalException {
    public InfrastructureException(String message, Throwable cause) {
        super(message, cause);
    }
}
