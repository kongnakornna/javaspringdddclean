package com.icmon.module.inventory.domain.valueobjects;

public record StockQuantity(int value) {
    public StockQuantity {
        if (value < 0) {
            throw new IllegalArgumentException("Stock quantity must not be negative");
        }
    }
}
