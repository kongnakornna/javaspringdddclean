package com.icmon.module.purchase.domain.valueobjects;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DeliveryDate {

    private final LocalDateTime expected;
    private final LocalDateTime actual;

    public DeliveryDate(final LocalDateTime expected, final LocalDateTime actual) {
        if (expected == null) {
            throw new IllegalArgumentException("วันที่จัดส่งที่คาดหวังต้องระบุ | Expected delivery date must not be null");
        }
        this.expected = expected;
        this.actual = actual;
    }

    public boolean isOnTime() {
        return actual != null && !actual.isAfter(expected);
    }

    public boolean isDelivered() {
        return actual != null;
    }
}
