package com.icmon.module.inventory.domain.valueobjects;

public record StockQuantity(Integer value) {
    public StockQuantity {
        if (value == null || value < 0) {
            throw new IllegalArgumentException("Stock quantity must not be negative");
        }
    }
}
