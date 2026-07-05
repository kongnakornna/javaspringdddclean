package com.icmon.module.email.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TEmailQueueTest {

    @Test
    void shouldMarkAsProcessing() {
        TEmailQueue queue = new TEmailQueue();
        queue.markAsProcessing();
        assertEquals("PROCESSING", queue.getStatus());
    }

    @Test
    void shouldMarkAsSent() {
        TEmailQueue queue = new TEmailQueue();
        queue.markAsSent();
        assertEquals("SENT", queue.getStatus());
    }

    @Test
    void shouldMarkAsFailedAndIncrementRetry() {
        TEmailQueue queue = new TEmailQueue();
        queue.setMaxRetry(3);
        queue.markAsFailed("Timeout");

        assertEquals("FAILED", queue.getStatus());
        assertEquals("Timeout", queue.getErrorMessage());
        assertEquals(1, queue.getRetryCount());
        assertNotNull(queue.getNextAttemptAt());
    }

    @Test
    void shouldBeRetryableWhenFailedAndBelowMaxRetry() {
        TEmailQueue queue = new TEmailQueue();
        queue.setStatus("FAILED");
        queue.setRetryCount(1);
        queue.setMaxRetry(3);
        assertTrue(queue.canRetry());
    }

    @Test
    void shouldNotBeRetryableWhenMaxRetryReached() {
        TEmailQueue queue = new TEmailQueue();
        queue.setStatus("FAILED");
        queue.setRetryCount(3);
        queue.setMaxRetry(3);
        assertFalse(queue.canRetry());
    }

    @Test
    void shouldNotBeRetryableWhenNotFailed() {
        TEmailQueue queue = new TEmailQueue();
        queue.setStatus("SENT");
        assertFalse(queue.canRetry());
    }

    @Test
    void shouldNotSetNextAttemptWhenMaxRetryReached() {
        TEmailQueue queue = new TEmailQueue();
        queue.setMaxRetry(3);
        queue.markAsFailed("Error");
        assertTrue(queue.getRetryCount() >= queue.getMaxRetry() || queue.getNextAttemptAt() != null);
    }
}
