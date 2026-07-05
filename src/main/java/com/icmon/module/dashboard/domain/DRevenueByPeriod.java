package com.icmon.module.dashboard.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class DRevenueByPeriod {
    private UUID whitelabelId;
    private LocalDate period;
    private Integer invoiceCount;
    private BigDecimal revenue;
    private BigDecimal averageRevenue;

    public UUID getWhitelabelId() { return whitelabelId; }
    public void setWhitelabelId(UUID whitelabelId) { this.whitelabelId = whitelabelId; }
    public LocalDate getPeriod() { return period; }
    public void setPeriod(LocalDate period) { this.period = period; }
    public Integer getInvoiceCount() { return invoiceCount; }
    public void setInvoiceCount(Integer invoiceCount) { this.invoiceCount = invoiceCount; }
    public BigDecimal getRevenue() { return revenue; }
    public void setRevenue(BigDecimal revenue) { this.revenue = revenue; }
    public BigDecimal getAverageRevenue() { return averageRevenue; }
    public void setAverageRevenue(BigDecimal averageRevenue) { this.averageRevenue = averageRevenue; }
}
