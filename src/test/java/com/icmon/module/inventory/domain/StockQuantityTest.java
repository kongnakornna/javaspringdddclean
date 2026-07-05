package com.icmon.module.inventory.domain;

import com.icmon.module.inventory.domain.valueobjects.StockQuantity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StockQuantityTest {

    @Test
    void shouldCreateValidQuantity() {
        StockQuantity qty = new StockQuantity(10);
        assertEquals(10, qty.value());
    }

    @Test
    void shouldRejectNegativeQuantity() {
        assertThrows(IllegalArgumentException.class, () -> new StockQuantity(-1));
    }

    @Test
    void shouldAllowZeroQuantity() {
        assertDoesNotThrow(() -> new StockQuantity(0));
    }
}
