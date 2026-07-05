package com.icmon.module.inventory.domain.valueobjects;

public record PickingNo(String value) {
    public PickingNo {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Picking number must not be null or blank");
        }
        if (!value.matches("PK-\\d{4}-\\d{4}")) {
            throw new IllegalArgumentException("Picking number must follow format PK-YYYY-XXXX");
        }
    }
}
