package com.icmon.exception.models;

import com.icmon.exception.SystemGlobalException;

public class FailedRequestException extends SystemGlobalException {
    public FailedRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
