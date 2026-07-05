package com.icmon.module.inventory.domain;

import com.icmon.module.inventory.domain.valueobjects.PartCode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PartCodeTest {

    @Test
    void shouldCreateValidPartCode() {
        PartCode code = new PartCode("BRK-001");
        assertEquals("BRK-001", code.value());
    }

    @Test
    void shouldRejectNullPartCode() {
        assertThrows(IllegalArgumentException.class, () -> new PartCode(null));
    }

    @Test
    void shouldRejectBlankPartCode() {
        assertThrows(IllegalArgumentException.class, () -> new PartCode(" "));
    }

    @Test
    void shouldRejectTooLongPartCode() {
        String longCode = "A".repeat(51);
        assertThrows(IllegalArgumentException.class, () -> new PartCode(longCode));
    }
}
