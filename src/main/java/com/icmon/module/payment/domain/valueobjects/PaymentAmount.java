package com.icmon.module.payment.domain.valueobjects;

import java.math.BigDecimal;

public record PaymentAmount(BigDecimal value) {
    public PaymentAmount {
        if (value == null) {
            throw new IllegalArgumentException("Payment amount must not be null");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Payment amount must not be negative");
        }
    }
}
