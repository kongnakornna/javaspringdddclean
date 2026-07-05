package com.icmon.module.payment.domain;

import com.icmon.module.payment.domain.enums.PaymentStatus;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class TPaymentTest {

    @Test
    void shouldApprovePayment() {
        TPayment payment = new TPayment();
        payment.setStatus(PaymentStatus.PENDING);
        payment.setAmount(new BigDecimal("500"));
        UUID approverId = UUID.randomUUID();
        payment.approve(approverId);
        assertEquals(PaymentStatus.COMPLETED, payment.getStatus());
        assertEquals(approverId, payment.getApprovedBy());
        assertNotNull(payment.getApprovedAt());
    }

    @Test
    void shouldNotApproveAlreadyCompletedPayment() {
        TPayment payment = new TPayment();
        payment.setStatus(PaymentStatus.COMPLETED);
        assertThrows(IllegalStateException.class, () -> payment.approve(UUID.randomUUID()));
    }

    @Test
    void shouldProcessRefund() {
        TPayment payment = new TPayment();
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setAmount(new BigDecimal("1000"));
        payment.processRefund(new BigDecimal("500"));
        assertEquals(PaymentStatus.REFUNDED, payment.getStatus());
        assertEquals(0, new BigDecimal("500").compareTo(payment.getRefundedAmount()));
        assertNotNull(payment.getRefundedAt());
    }

    @Test
    void shouldNotRefundPendingPayment() {
        TPayment payment = new TPayment();
        payment.setStatus(PaymentStatus.PENDING);
        payment.setAmount(new BigDecimal("1000"));
        assertThrows(IllegalStateException.class, () -> payment.processRefund(new BigDecimal("500")));
    }

    @Test
    void shouldNotRefundWithNegativeAmount() {
        TPayment payment = new TPayment();
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setAmount(new BigDecimal("1000"));
        assertThrows(IllegalArgumentException.class, () -> payment.processRefund(new BigDecimal("-100")));
    }

    @Test
    void shouldNotRefundExceedingAmount() {
        TPayment payment = new TPayment();
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setAmount(new BigDecimal("1000"));
        assertThrows(IllegalArgumentException.class, () -> payment.processRefund(new BigDecimal("2000")));
    }

    @Test
    void shouldReturnRemainingRefundable() {
        TPayment payment = new TPayment();
        payment.setAmount(new BigDecimal("1000"));
        assertEquals(0, new BigDecimal("1000").compareTo(payment.getRemainingRefundable()));
        payment.setRefundedAmount(new BigDecimal("300"));
        assertEquals(0, new BigDecimal("700").compareTo(payment.getRemainingRefundable()));
    }

    @Test
    void shouldDetectApprovableStatus() {
        TPayment payment = new TPayment();
        payment.setStatus(PaymentStatus.PENDING);
        assertTrue(payment.canApprove());
        payment.setStatus(PaymentStatus.COMPLETED);
        assertFalse(payment.canApprove());
    }

    @Test
    void shouldDetectRefundableStatus() {
        TPayment payment = new TPayment();
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setAmount(new BigDecimal("500"));
        assertTrue(payment.canRefund());
        payment.setRefundedAmount(new BigDecimal("500"));
        assertFalse(payment.canRefund());
    }
}
