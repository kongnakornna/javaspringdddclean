package com.icmon.module.quotation.domain.valueobjects;

import java.math.BigDecimal;
import java.util.Currency;

/*
    มูลค่าจำนวนเงินพร้อมสกุลเงิน / Amount value object with currency.
*/
public class Amount {
    private final BigDecimal value;
    private final Currency currency;

    public Amount(BigDecimal value, Currency currency) {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }
        this.value = value;
        this.currency = currency;
    }

    public Amount(BigDecimal value) {
        this(value, Currency.getInstance("THB"));
    }

    public BigDecimal getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Amount add(Amount other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        return new Amount(this.value.add(other.value), this.currency);
    }

    public Amount subtract(Amount other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        return new Amount(this.value.subtract(other.value), this.currency);
    }

    public Amount multiply(BigDecimal multiplier) {
        return new Amount(this.value.multiply(multiplier), this.currency);
    }
}
