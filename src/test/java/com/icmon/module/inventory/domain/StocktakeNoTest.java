package com.icmon.module.inventory.domain;

import com.icmon.module.inventory.domain.valueobjects.StocktakeNo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StocktakeNoTest {

    @Test
    void shouldCreateValidStocktakeNo() {
        StocktakeNo no = new StocktakeNo("ST-2024-0001");
        assertEquals("ST-2024-0001", no.value());
    }

    @Test
    void shouldRejectInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> new StocktakeNo("INVALID"));
    }
}
