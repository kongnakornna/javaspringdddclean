package com.icmon.module.dashboard.domain.valueobjects;

import java.time.LocalDate;

public record DateRange(LocalDate startDate, LocalDate endDate) {
    public DateRange {
        if (startDate == null) {
            throw new IllegalArgumentException("startDate must not be null");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("endDate must not be null");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("endDate must not be before startDate");
        }
    }

    public static DateRange of(String start, String end) {
        LocalDate startDate = start != null ? LocalDate.parse(start) : LocalDate.now().minusMonths(1);
        LocalDate endDate = end != null ? LocalDate.parse(end) : LocalDate.now();
        return new DateRange(startDate, endDate);
    }
}
