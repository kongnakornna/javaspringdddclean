package com.icmon.module.email.domain;

import com.icmon.module.email.domain.enums.EmailStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TEmailHistoryTest {

    @Test
    void shouldMarkAsSent() {
        TEmailHistory history = new TEmailHistory();
        history.markAsSent();

        assertEquals(EmailStatus.SENT, history.getStatus());
        assertNotNull(history.getSentAt());
    }

    @Test
    void shouldMarkAsFailed() {
        TEmailHistory history = new TEmailHistory();
        history.markAsFailed("Connection timeout");

        assertEquals(EmailStatus.FAILED, history.getStatus());
        assertEquals("Connection timeout", history.getErrorMessage());
        assertEquals(1, history.getRetryCount());
    }

    @Test
    void shouldIncrementRetryCountOnFailure() {
        TEmailHistory history = new TEmailHistory();
        history.setRetryCount(2);
        history.markAsFailed("Error");

        assertEquals(3, history.getRetryCount());
    }

    @Test
    void shouldMarkAsBounced() {
        TEmailHistory history = new TEmailHistory();
        history.markAsBounced("Mailbox full");

        assertEquals(EmailStatus.BOUNCED, history.getStatus());
        assertEquals("Mailbox full", history.getErrorMessage());
    }

    @Test
    void shouldBeResendableWhenFailed() {
        TEmailHistory history = new TEmailHistory();
        history.setStatus(EmailStatus.FAILED);
        assertTrue(history.canResend());
    }

    @Test
    void shouldBeResendableWhenBounced() {
        TEmailHistory history = new TEmailHistory();
        history.setStatus(EmailStatus.BOUNCED);
        assertTrue(history.canResend());
    }

    @Test
    void shouldNotBeResendableWhenSent() {
        TEmailHistory history = new TEmailHistory();
        history.setStatus(EmailStatus.SENT);
        assertFalse(history.canResend());
    }

    @Test
    void shouldNotBeResendableWhenPending() {
        TEmailHistory history = new TEmailHistory();
        history.setStatus(EmailStatus.PENDING);
        assertFalse(history.canResend());
    }

    @Test
    void shouldNotBeResendableAfterMaxRetries() {
        TEmailHistory history = new TEmailHistory();
        history.setStatus(EmailStatus.FAILED);
        history.setRetryCount(3);
        assertFalse(history.canResend());
    }

    @Test
    void shouldBeResendableWhenRetryCountLessThanThree() {
        TEmailHistory history = new TEmailHistory();
        history.setStatus(EmailStatus.FAILED);
        history.setRetryCount(2);
        assertTrue(history.canResend());
    }
}
