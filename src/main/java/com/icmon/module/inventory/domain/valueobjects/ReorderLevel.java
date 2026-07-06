package com.icmon.module.inventory.domain.valueobjects;

public record ReorderLevel(Integer value) {
    public ReorderLevel {
        if (value == null || value < 0) {
            throw new IllegalArgumentException("Reorder level must not be negative");
        }
    }
}
