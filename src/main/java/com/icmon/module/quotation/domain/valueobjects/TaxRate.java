package com.icmon.module.quotation.domain.valueobjects;

import java.math.BigDecimal;

/*
    มูลค่าอัตราภาษี / Tax rate value object.
*/
public class TaxRate {
    private final BigDecimal rate;

    public TaxRate(BigDecimal rate) {
        if (rate == null || rate.compareTo(BigDecimal.ZERO) < 0 || rate.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("Tax rate must be between 0 and 100");
        }
        this.rate = rate;
    }

    public TaxRate(double rate) {
        this(BigDecimal.valueOf(rate));
    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal calculateTax(BigDecimal amount) {
        return amount.multiply(rate).divide(new BigDecimal("100"));
    }
}
