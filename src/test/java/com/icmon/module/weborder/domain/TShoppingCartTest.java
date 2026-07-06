package com.icmon.module.weborder.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TShoppingCartTest {

    @Test
    void testCalculateTotals() {
        TShoppingCart cart = new TShoppingCart();
        cart.setTax(new BigDecimal("70.00"));
        cart.setShipping(new BigDecimal("50.00"));
        cart.setDiscount(new BigDecimal("100.00"));

        TShoppingCartItem item = new TShoppingCartItem();
        item.setCartId(UUID.randomUUID());
        item.setItemId(UUID.randomUUID());
        item.setQuantity(2);
        item.setUnitPrice(new BigDecimal("500.00"));
        item.setTotalPrice(new BigDecimal("1000.00"));
        cart.getItems().add(item);

        cart.calculateTotals();
        assertEquals(new BigDecimal("1000.00"), cart.getSubtotal());
        assertEquals(new BigDecimal("1020.00"), cart.getTotal());
    }

    @Test
    void testCalculateTotalsWithZeroTax() {
        TShoppingCart cart = new TShoppingCart();
        cart.calculateTotals();
        assertEquals(BigDecimal.ZERO, cart.getSubtotal());
        assertEquals(BigDecimal.ZERO, cart.getTotal());
    }
}
