package com.icmon.exception.models;

import com.icmon.exception.SystemGlobalException;


public class ApplicationException extends SystemGlobalException {
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
