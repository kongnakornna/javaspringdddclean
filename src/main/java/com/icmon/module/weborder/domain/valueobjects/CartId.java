package com.icmon.module.weborder.domain.valueobjects;

import lombok.Value;

@Value
public class CartId {
    String value;

    public CartId(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("CartId must not be null or blank");
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
