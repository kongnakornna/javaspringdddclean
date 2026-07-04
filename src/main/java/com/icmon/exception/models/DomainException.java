package com.icmon.exception.models;

import com.icmon.exception.SystemGlobalException;


public class DomainException extends SystemGlobalException {
    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
