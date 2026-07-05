package com.icmon.module.inventory.domain.valueobjects;

import java.math.BigDecimal;

public record UnitCost(BigDecimal value) {
    public UnitCost {
        if (value == null) {
            throw new IllegalArgumentException("Unit cost must not be null");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Unit cost must not be negative");
        }
    }
}
