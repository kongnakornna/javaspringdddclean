package com.icmon.module.payment.domain;

import com.icmon.module.payment.domain.valueobjects.PaymentAmount;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class PaymentAmountTest {

    @Test
    void shouldCreateValidPaymentAmount() {
        PaymentAmount amount = new PaymentAmount(new BigDecimal("100.00"));
        assertEquals(0, new BigDecimal("100.00").compareTo(amount.value()));
    }

    @Test
    void shouldRejectNullPaymentAmount() {
        assertThrows(IllegalArgumentException.class, () -> new PaymentAmount(null));
    }

    @Test
    void shouldRejectNegativePaymentAmount() {
        assertThrows(IllegalArgumentException.class, () -> new PaymentAmount(new BigDecimal("-1")));
    }

    @Test
    void shouldAllowZeroPaymentAmount() {
        assertDoesNotThrow(() -> new PaymentAmount(BigDecimal.ZERO));
    }
}
