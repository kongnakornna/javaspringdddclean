package com.icmon.module.auth.infrastructure.ratelimit;

public class RateLimitExceededException extends Exception {
    public RateLimitExceededException(String message) {
        super(message);
    }
}
