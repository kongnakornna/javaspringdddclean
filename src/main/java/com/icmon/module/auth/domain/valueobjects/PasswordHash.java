package com.icmon.module.auth.domain.valueobjects;

import lombok.Value;

@Value
public class PasswordHash {
    String value;

    public PasswordHash(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Password hash must not be blank");
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
