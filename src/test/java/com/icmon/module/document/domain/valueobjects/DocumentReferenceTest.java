package com.icmon.module.document.domain.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentReferenceTest {

    @Test
    void shouldCreateValidReference() {
        DocumentReference ref = new DocumentReference("INVOICE", java.util.UUID.randomUUID());
        assertEquals("INVOICE", ref.referenceType());
        assertNotNull(ref.referenceId());
    }

    @Test
    void shouldRejectNullReferenceType() {
        assertThrows(IllegalArgumentException.class,
                () -> new DocumentReference(null, java.util.UUID.randomUUID()));
    }

    @Test
    void shouldRejectBlankReferenceType() {
        assertThrows(IllegalArgumentException.class,
                () -> new DocumentReference(" ", java.util.UUID.randomUUID()));
    }

    @Test
    void shouldRejectNullReferenceId() {
        assertThrows(IllegalArgumentException.class,
                () -> new DocumentReference("INVOICE", null));
    }
}
