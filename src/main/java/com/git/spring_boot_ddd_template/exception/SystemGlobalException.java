package com.git.spring_boot_ddd_template.exception;

public class SystemGlobalException extends Exception {
    public SystemGlobalException(String message, Throwable cause) {
        super(message, cause);
    }
}
