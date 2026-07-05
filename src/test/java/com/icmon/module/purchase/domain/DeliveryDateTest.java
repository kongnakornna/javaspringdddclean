package com.icmon.module.purchase.domain;

import com.icmon.module.purchase.domain.valueobjects.DeliveryDate;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryDateTest {

    @Test
    void testValidDeliveryDate() {
        LocalDateTime expected = LocalDateTime.now().plusDays(7);
        DeliveryDate deliveryDate = new DeliveryDate(expected, null);
        assertEquals(expected, deliveryDate.getExpected());
        assertNull(deliveryDate.getActual());
    }

    @Test
    void testNullExpectedThrows() {
        assertThrows(IllegalArgumentException.class, () -> new DeliveryDate(null, null));
    }

    @Test
    void testEarlyDeliveryIsOnTime() {
        LocalDateTime expected = LocalDateTime.now().plusDays(7);
        LocalDateTime actual = LocalDateTime.now().plusDays(5);
        DeliveryDate deliveryDate = new DeliveryDate(expected, actual);
        assertTrue(deliveryDate.isOnTime());
    }

    @Test
    void testOnTime() {
        LocalDateTime expected = LocalDateTime.now().plusDays(5);
        LocalDateTime actual = LocalDateTime.now().plusDays(5);
        DeliveryDate deliveryDate = new DeliveryDate(expected, actual);
        assertTrue(deliveryDate.isOnTime());
    }

    @Test
    void testLate() {
        LocalDateTime expected = LocalDateTime.now().plusDays(3);
        LocalDateTime actual = LocalDateTime.now().plusDays(5);
        DeliveryDate deliveryDate = new DeliveryDate(expected, actual);
        assertFalse(deliveryDate.isOnTime());
    }

    @Test
    void testNotDelivered() {
        LocalDateTime expected = LocalDateTime.now().plusDays(7);
        DeliveryDate deliveryDate = new DeliveryDate(expected, null);
        assertFalse(deliveryDate.isDelivered());
    }
}
