package com.icmon.module.dashboard.domain.valueobjects;

public record Period(String value) {
    public Period {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Period must not be null or blank");
        }
        String upper = value.toUpperCase();
        if (!"DAY".equals(upper) && !"MONTH".equals(upper) && !"YEAR".equals(upper)) {
            throw new IllegalArgumentException("Period must be DAY, MONTH, or YEAR");
        }
    }
}
