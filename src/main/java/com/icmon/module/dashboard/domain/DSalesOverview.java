package com.icmon.module.dashboard.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

public class DSalesOverview {
    private UUID whitelabelId;
    private LocalDate period;
    private Integer totalInvoices;
    private Integer totalCustomers;
    private BigDecimal totalRevenue;
    private BigDecimal totalOutstanding;
    private BigDecimal averageInvoice;

    public BigDecimal getGrowthRate(DSalesOverview previous) {
        if (previous == null || previous.getTotalRevenue().compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return this.totalRevenue.subtract(previous.getTotalRevenue())
                .divide(previous.getTotalRevenue(), 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100));
    }

    public UUID getWhitelabelId() { return whitelabelId; }
    public void setWhitelabelId(UUID whitelabelId) { this.whitelabelId = whitelabelId; }
    public LocalDate getPeriod() { return period; }
    public void setPeriod(LocalDate period) { this.period = period; }
    public Integer getTotalInvoices() { return totalInvoices; }
    public void setTotalInvoices(Integer totalInvoices) { this.totalInvoices = totalInvoices; }
    public Integer getTotalCustomers() { return totalCustomers; }
    public void setTotalCustomers(Integer totalCustomers) { this.totalCustomers = totalCustomers; }
    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }
    public BigDecimal getTotalOutstanding() { return totalOutstanding; }
    public void setTotalOutstanding(BigDecimal totalOutstanding) { this.totalOutstanding = totalOutstanding; }
    public BigDecimal getAverageInvoice() { return averageInvoice; }
    public void setAverageInvoice(BigDecimal averageInvoice) { this.averageInvoice = averageInvoice; }
}
