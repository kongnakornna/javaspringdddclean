package com.icmon.module.payment.domain.valueobjects;

public record TransactionReference(String value) {
    public TransactionReference {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Transaction reference must not be null or blank");
        }
        if (value.length() > 50) {
            throw new IllegalArgumentException("Transaction reference must not exceed 50 characters");
        }
    }
}
