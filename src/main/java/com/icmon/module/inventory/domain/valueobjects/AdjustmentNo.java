package com.icmon.module.inventory.domain.valueobjects;

public record AdjustmentNo(String value) {
    public AdjustmentNo {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Adjustment number must not be null or blank");
        }
        if (!value.matches("ADJ-\\d{4}-\\d{4}")) {
            throw new IllegalArgumentException("Adjustment number must follow format ADJ-YYYY-XXXX");
        }
    }
}
