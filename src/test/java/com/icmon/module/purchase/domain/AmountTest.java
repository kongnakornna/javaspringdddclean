package com.icmon.module.purchase.domain;

import com.icmon.module.purchase.domain.valueobjects.Amount;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AmountTest {

    @Test
    void testValidAmount() {
        Amount amount = Amount.of(100.00);
        assertEquals(new BigDecimal("100.00"), amount.getValue());
    }

    @Test
    void testNegativeAmountThrows() {
        assertThrows(IllegalArgumentException.class, () -> Amount.of(-1.00));
    }

    @Test
    void testNullAmountThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Amount(null));
    }

    @Test
    void testAdd() {
        Amount a = Amount.of(100);
        Amount b = Amount.of(50);
        assertEquals(new BigDecimal("150.00"), a.add(b).getValue());
    }

    @Test
    void testSubtract() {
        Amount a = Amount.of(100);
        Amount b = Amount.of(30);
        assertEquals(new BigDecimal("70.00"), a.subtract(b).getValue());
    }

    @Test
    void testMultiply() {
        Amount a = Amount.of(100);
        assertEquals(new BigDecimal("250.00"), a.multiply(new BigDecimal("2.5")).getValue());
    }
}
