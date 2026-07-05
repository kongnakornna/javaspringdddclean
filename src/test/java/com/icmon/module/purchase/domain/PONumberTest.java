package com.icmon.module.purchase.domain;

import com.icmon.module.purchase.domain.valueobjects.PONumber;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PONumberTest {

    @Test
    void testValidPONumber() {
        PONumber poNo = new PONumber("PO-2026-0001");
        assertEquals("PO-2026-0001", poNo.getValue());
    }

    @Test
    void testInvalidPONumberFormat() {
        assertThrows(IllegalArgumentException.class, () -> new PONumber("INVALID"));
    }

    @Test
    void testNullPONumber() {
        assertThrows(IllegalArgumentException.class, () -> new PONumber(null));
    }

    @Test
    void testBlankPONumber() {
        assertThrows(IllegalArgumentException.class, () -> new PONumber(" "));
    }

    @Test
    void testEquality() {
        PONumber poNo1 = new PONumber("PO-2026-0001");
        PONumber poNo2 = new PONumber("PO-2026-0001");
        assertEquals(poNo1, poNo2);
        assertEquals(poNo1.hashCode(), poNo2.hashCode());
    }
}
