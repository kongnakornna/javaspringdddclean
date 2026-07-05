package com.icmon.module.payment.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.payment.domain.enums.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class TPayment extends GenericBusinessClass {
    private String paymentNo;
    private UUID invoiceId;
    private UUID jobId;
    private UUID customerId;
    private LocalDateTime paymentDate;
    private UUID paymentMethodId;
    private BigDecimal amount;
    private BigDecimal amountReceived;
    private BigDecimal changeAmount;
    private String currency;
    private BigDecimal exchangeRate;
    private PaymentStatus status;
    private String referenceNumber;
    private String bankName;
    private String chequeNumber;
    private String chequeBank;
    private LocalDate chequeDate;
    private String notes;
    private UUID receivedBy;
    private UUID approvedBy;
    private LocalDateTime approvedAt;
    private BigDecimal refundedAmount;
    private LocalDateTime refundedAt;

    public boolean canApprove() {
        return this.status == PaymentStatus.PENDING;
    }

    public boolean canRefund() {
        return this.status == PaymentStatus.COMPLETED
                && (this.refundedAmount == null || this.refundedAmount.compareTo(BigDecimal.ZERO) == 0)
                && this.amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public void approve(UUID approverId) {
        if (!canApprove()) {
            throw new IllegalStateException("Cannot approve payment with status: " + this.status);
        }
        this.status = PaymentStatus.COMPLETED;
        this.approvedBy = approverId;
        this.approvedAt = LocalDateTime.now();
    }

    public void processRefund(BigDecimal refundAmount) {
        if (!canRefund()) {
            throw new IllegalStateException("Cannot refund payment with status: " + this.status);
        }
        if (refundAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Refund amount must be greater than zero");
        }
        if (refundAmount.compareTo(this.amount) > 0) {
            throw new IllegalArgumentException("Refund amount exceeds paid amount");
        }
        this.refundedAmount = refundAmount;
        this.refundedAt = LocalDateTime.now();
        this.status = PaymentStatus.REFUNDED;
    }

    public BigDecimal getRemainingRefundable() {
        if (this.refundedAmount == null) {
            return this.amount;
        }
        return this.amount.subtract(this.refundedAmount);
    }

    public String getPaymentNo() { return paymentNo; }
    public void setPaymentNo(String paymentNo) { this.paymentNo = paymentNo; }
    public UUID getInvoiceId() { return invoiceId; }
    public void setInvoiceId(UUID invoiceId) { this.invoiceId = invoiceId; }
    public UUID getJobId() { return jobId; }
    public void setJobId(UUID jobId) { this.jobId = jobId; }
    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }
    public UUID getPaymentMethodId() { return paymentMethodId; }
    public void setPaymentMethodId(UUID paymentMethodId) { this.paymentMethodId = paymentMethodId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public BigDecimal getAmountReceived() { return amountReceived; }
    public void setAmountReceived(BigDecimal amountReceived) { this.amountReceived = amountReceived; }
    public BigDecimal getChangeAmount() { return changeAmount; }
    public void setChangeAmount(BigDecimal changeAmount) { this.changeAmount = changeAmount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public BigDecimal getExchangeRate() { return exchangeRate; }
    public void setExchangeRate(BigDecimal exchangeRate) { this.exchangeRate = exchangeRate; }
    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }
    public String getReferenceNumber() { return referenceNumber; }
    public void setReferenceNumber(String referenceNumber) { this.referenceNumber = referenceNumber; }
    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }
    public String getChequeNumber() { return chequeNumber; }
    public void setChequeNumber(String chequeNumber) { this.chequeNumber = chequeNumber; }
    public String getChequeBank() { return chequeBank; }
    public void setChequeBank(String chequeBank) { this.chequeBank = chequeBank; }
    public LocalDate getChequeDate() { return chequeDate; }
    public void setChequeDate(LocalDate chequeDate) { this.chequeDate = chequeDate; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public UUID getReceivedBy() { return receivedBy; }
    public void setReceivedBy(UUID receivedBy) { this.receivedBy = receivedBy; }
    public UUID getApprovedBy() { return approvedBy; }
    public void setApprovedBy(UUID approvedBy) { this.approvedBy = approvedBy; }
    public LocalDateTime getApprovedAt() { return approvedAt; }
    public void setApprovedAt(LocalDateTime approvedAt) { this.approvedAt = approvedAt; }
    public BigDecimal getRefundedAmount() { return refundedAmount; }
    public void setRefundedAmount(BigDecimal refundedAmount) { this.refundedAmount = refundedAmount; }
    public LocalDateTime getRefundedAt() { return refundedAt; }
    public void setRefundedAt(LocalDateTime refundedAt) { this.refundedAt = refundedAt; }
}
