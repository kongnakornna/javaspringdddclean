package com.icmon.module.payment.domain;

import com.icmon.module.payment.domain.valueobjects.TransactionReference;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TransactionReferenceTest {

    @Test
    void shouldCreateValidReference() {
        TransactionReference ref = new TransactionReference("TXN-001");
        assertEquals("TXN-001", ref.value());
    }

    @Test
    void shouldRejectNullReference() {
        assertThrows(IllegalArgumentException.class, () -> new TransactionReference(null));
    }

    @Test
    void shouldRejectBlankReference() {
        assertThrows(IllegalArgumentException.class, () -> new TransactionReference(" "));
    }

    @Test
    void shouldRejectTooLongReference() {
        String longRef = "A".repeat(51);
        assertThrows(IllegalArgumentException.class, () -> new TransactionReference(longRef));
    }
}
