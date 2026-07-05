package com.icmon.module.inventory.domain.valueobjects;

public record PartCode(String value) {
    public PartCode {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Part code must not be null or blank");
        }
        if (value.length() > 50) {
            throw new IllegalArgumentException("Part code must not exceed 50 characters");
        }
    }
}
