package com.icmon.module.payment.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TReceipt extends GenericBusinessClass {
    private String receiptNo;
    private UUID paymentId;
    private UUID invoiceId;
    private UUID customerId;
    private LocalDateTime receiptDate;
    private String receiptType;
    private BigDecimal amount;
    private String amountInWordsTh;
    private String amountInWordsEn;
    private String currency;
    private String status;
    private String notes;
    private UUID issuedBy;

    public void issue() {
        if (!"DRAFT".equals(this.status)) {
            throw new IllegalStateException("Cannot issue receipt with status: " + this.status);
        }
        this.status = "ISSUED";
        this.receiptDate = LocalDateTime.now();
    }

    public void cancel(String reason) {
        if ("CANCELLED".equals(this.status)) {
            throw new IllegalStateException("Receipt is already cancelled");
        }
        this.status = "CANCELLED";
        this.notes = (this.notes != null ? this.notes + "\n" : "") + "Cancelled: " + reason;
    }

    public boolean canCancel() {
        return "ISSUED".equals(this.status) || "DRAFT".equals(this.status);
    }

    public String getReceiptNo() { return receiptNo; }
    public void setReceiptNo(String receiptNo) { this.receiptNo = receiptNo; }
    public UUID getPaymentId() { return paymentId; }
    public void setPaymentId(UUID paymentId) { this.paymentId = paymentId; }
    public UUID getInvoiceId() { return invoiceId; }
    public void setInvoiceId(UUID invoiceId) { this.invoiceId = invoiceId; }
    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }
    public LocalDateTime getReceiptDate() { return receiptDate; }
    public void setReceiptDate(LocalDateTime receiptDate) { this.receiptDate = receiptDate; }
    public String getReceiptType() { return receiptType; }
    public void setReceiptType(String receiptType) { this.receiptType = receiptType; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getAmountInWordsTh() { return amountInWordsTh; }
    public void setAmountInWordsTh(String amountInWordsTh) { this.amountInWordsTh = amountInWordsTh; }
    public String getAmountInWordsEn() { return amountInWordsEn; }
    public void setAmountInWordsEn(String amountInWordsEn) { this.amountInWordsEn = amountInWordsEn; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public UUID getIssuedBy() { return issuedBy; }
    public void setIssuedBy(UUID issuedBy) { this.issuedBy = issuedBy; }
}
