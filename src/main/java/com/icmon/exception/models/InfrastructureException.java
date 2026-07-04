package com.icmon.exception.models;

import com.icmon.exception.SystemGlobalException;


public class InfrastructureException extends SystemGlobalException {
    public InfrastructureException(String message, Throwable cause) {
        super(message, cause);
    }
}
