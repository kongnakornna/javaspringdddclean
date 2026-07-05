package com.icmon.module.dashboard.domain;

import com.icmon.module.dashboard.domain.valueobjects.DateRange;
import com.icmon.module.dashboard.domain.valueobjects.Period;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class DSalesOverviewTest {

    @Test
    void shouldCalculateGrowthRate() {
        DSalesOverview current = new DSalesOverview();
        current.setTotalRevenue(new BigDecimal("1200"));
        DSalesOverview previous = new DSalesOverview();
        previous.setTotalRevenue(new BigDecimal("1000"));
        assertEquals(0, new BigDecimal("20.0000").compareTo(current.getGrowthRate(previous)));
    }

    @Test
    void shouldReturnZeroGrowthWhenPreviousNull() {
        DSalesOverview current = new DSalesOverview();
        current.setTotalRevenue(new BigDecimal("1000"));
        assertEquals(BigDecimal.ZERO, current.getGrowthRate(null));
    }
}

class DJobStatusSummaryTest {

    @Test
    void shouldCalculatePercentage() {
        DJobStatusSummary summary = new DJobStatusSummary();
        summary.setStatus("OPEN");
        summary.setCount(30L);
        assertEquals(30.0, summary.getPercentage(100L), 0.001);
    }

    @Test
    void shouldReturnZeroWhenTotalNull() {
        DJobStatusSummary summary = new DJobStatusSummary();
        summary.setCount(10L);
        assertEquals(0.0, summary.getPercentage(null), 0.001);
    }
}

class DateRangeTest {

    @Test
    void shouldCreateValidDateRange() {
        assertDoesNotThrow(() -> new DateRange(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31)));
    }

    @Test
    void shouldRejectNullStartDate() {
        assertThrows(IllegalArgumentException.class, () -> new DateRange(null, LocalDate.now()));
    }

    @Test
    void shouldRejectEndBeforeStart() {
        assertThrows(IllegalArgumentException.class,
            () -> new DateRange(LocalDate.of(2024, 12, 31), LocalDate.of(2024, 1, 1)));
    }
}

class PeriodTest {

    @Test
    void shouldCreateValidPeriod() {
        assertDoesNotThrow(() -> new Period("MONTH"));
    }

    @Test
    void shouldRejectNullPeriod() {
        assertThrows(IllegalArgumentException.class, () -> new Period(null));
    }

    @Test
    void shouldRejectInvalidPeriod() {
        assertThrows(IllegalArgumentException.class, () -> new Period("WEEKLY"));
    }
}
