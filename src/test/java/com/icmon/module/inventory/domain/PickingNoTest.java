package com.icmon.module.inventory.domain;

import com.icmon.module.inventory.domain.valueobjects.PickingNo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PickingNoTest {

    @Test
    void shouldCreateValidPickingNo() {
        PickingNo no = new PickingNo("PK-2024-0001");
        assertEquals("PK-2024-0001", no.value());
    }

    @Test
    void shouldRejectInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> new PickingNo("INVALID"));
    }
}
