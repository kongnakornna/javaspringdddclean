package com.icmon.module.payment.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TReceiptTest {

    @Test
    void shouldIssueReceipt() {
        TReceipt receipt = new TReceipt();
        receipt.setStatus("DRAFT");
        receipt.issue();
        assertEquals("ISSUED", receipt.getStatus());
        assertNotNull(receipt.getReceiptDate());
    }

    @Test
    void shouldNotIssueAlreadyIssuedReceipt() {
        TReceipt receipt = new TReceipt();
        receipt.setStatus("ISSUED");
        assertThrows(IllegalStateException.class, receipt::issue);
    }

    @Test
    void shouldCancelReceipt() {
        TReceipt receipt = new TReceipt();
        receipt.setStatus("ISSUED");
        receipt.cancel("Customer request");
        assertEquals("CANCELLED", receipt.getStatus());
    }

    @Test
    void shouldNotCancelAlreadyCancelledReceipt() {
        TReceipt receipt = new TReceipt();
        receipt.setStatus("CANCELLED");
        assertThrows(IllegalStateException.class, () -> receipt.cancel("Duplicate"));
    }

    @Test
    void shouldDetectCancelableStatus() {
        TReceipt receipt = new TReceipt();
        receipt.setStatus("DRAFT");
        assertTrue(receipt.canCancel());
        receipt.setStatus("ISSUED");
        assertTrue(receipt.canCancel());
        receipt.setStatus("CANCELLED");
        assertFalse(receipt.canCancel());
    }
}
