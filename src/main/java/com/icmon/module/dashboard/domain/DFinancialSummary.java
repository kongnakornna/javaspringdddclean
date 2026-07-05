package com.icmon.module.dashboard.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class DFinancialSummary {
    private LocalDate month;
    private BigDecimal totalInvoice;
    private BigDecimal totalPayment;
    private BigDecimal netIncome;
    private UUID whitelabelId;

    public LocalDate getMonth() { return month; }
    public void setMonth(LocalDate month) { this.month = month; }
    public BigDecimal getTotalInvoice() { return totalInvoice; }
    public void setTotalInvoice(BigDecimal totalInvoice) { this.totalInvoice = totalInvoice; }
    public BigDecimal getTotalPayment() { return totalPayment; }
    public void setTotalPayment(BigDecimal totalPayment) { this.totalPayment = totalPayment; }
    public BigDecimal getNetIncome() { return netIncome; }
    public void setNetIncome(BigDecimal netIncome) { this.netIncome = netIncome; }
    public UUID getWhitelabelId() { return whitelabelId; }
    public void setWhitelabelId(UUID whitelabelId) { this.whitelabelId = whitelabelId; }
}
