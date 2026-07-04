package com.icmon.exception.models;

import com.icmon.exception.SystemGlobalException;

public class AdapterException extends SystemGlobalException {
    public AdapterException(String message, Throwable cause) {
        super(message, cause);
    }
}
