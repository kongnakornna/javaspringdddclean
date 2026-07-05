package com.icmon.module.purchase.domain.valueobjects;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
public class Amount {

    private final BigDecimal value;

    public Amount(final BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("จำนวนเงินต้องไม่เป็นค่าว่าง | Amount must not be null");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("จำนวนเงินต้องไม่ติดลบ | Amount must not be negative");
        }
        this.value = value.setScale(2, RoundingMode.HALF_UP);
    }

    public static Amount of(final double value) {
        return new Amount(BigDecimal.valueOf(value));
    }

    public static Amount of(final BigDecimal value) {
        return new Amount(value);
    }

    public Amount add(final Amount other) {
        return new Amount(this.value.add(other.value));
    }

    public Amount subtract(final Amount other) {
        return new Amount(this.value.subtract(other.value));
    }

    public Amount multiply(final BigDecimal multiplier) {
        return new Amount(this.value.multiply(multiplier));
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amount amount = (Amount) o;
        return value.compareTo(amount.value) == 0;
    }

    @Override
    public int hashCode() {
        return value.stripTrailingZeros().hashCode();
    }
}
