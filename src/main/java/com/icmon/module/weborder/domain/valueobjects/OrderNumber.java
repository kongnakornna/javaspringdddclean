package com.icmon.module.weborder.domain.valueobjects;

import lombok.Value;

@Value
public class OrderNumber {
    String value;

    public OrderNumber(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("OrderNumber must not be null or blank");
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
