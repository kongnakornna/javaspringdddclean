package com.icmon.module.inventory.domain;

import com.icmon.module.inventory.domain.valueobjects.AdjustmentNo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdjustmentNoTest {

    @Test
    void shouldCreateValidAdjustmentNo() {
        AdjustmentNo no = new AdjustmentNo("ADJ-2024-0001");
        assertEquals("ADJ-2024-0001", no.value());
    }

    @Test
    void shouldRejectInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> new AdjustmentNo("INVALID"));
    }

    @Test
    void shouldRejectBlank() {
        assertThrows(IllegalArgumentException.class, () -> new AdjustmentNo(""));
    }
}
