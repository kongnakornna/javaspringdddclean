package com.icmon.module.inventory.domain.valueobjects;

public record PartCode(String value) {
    public PartCode {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Part code must not be blank");
        }
        if (!value.matches("[A-Z0-9\\-]{2,50}")) {
            throw new IllegalArgumentException("Part code must be 2-50 chars, uppercase, numbers, and hyphens only");
        }
    }
}
