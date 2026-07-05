package com.icmon.module.inventory.domain;

import com.icmon.module.inventory.domain.valueobjects.UnitCost;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class UnitCostTest {

    @Test
    void shouldCreateValidUnitCost() {
        UnitCost cost = new UnitCost(new BigDecimal("150.00"));
        assertEquals(0, new BigDecimal("150.00").compareTo(cost.value()));
    }

    @Test
    void shouldRejectNullUnitCost() {
        assertThrows(IllegalArgumentException.class, () -> new UnitCost(null));
    }

    @Test
    void shouldRejectNegativeUnitCost() {
        assertThrows(IllegalArgumentException.class, () -> new UnitCost(new BigDecimal("-10")));
    }
}
