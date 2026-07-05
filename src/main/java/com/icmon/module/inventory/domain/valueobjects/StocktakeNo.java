package com.icmon.module.inventory.domain.valueobjects;

public record StocktakeNo(String value) {
    public StocktakeNo {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Stocktake number must not be null or blank");
        }
        if (!value.matches("ST-\\d{4}-\\d{4}")) {
            throw new IllegalArgumentException("Stocktake number must follow format ST-YYYY-XXXX");
        }
    }
}
