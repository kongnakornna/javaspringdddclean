package com.icmon.module.inventory.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.inventory.domain.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Inventory extends GenericBusinessClass {
    private UUID partId;
    private TransactionType transactionType;
    private String referenceType;
    private UUID referenceId;
    private int quantity;
    private int previousQuantity;
    private int newQuantity;
    private BigDecimal unitCost;
    private BigDecimal totalCost;
    private LocalDateTime transactionDate;
    private String note;
    private UUID performedBy;

    public UUID getPartId() { return partId; }
    public void setPartId(UUID partId) { this.partId = partId; }
    public TransactionType getTransactionType() { return transactionType; }
    public void setTransactionType(TransactionType transactionType) { this.transactionType = transactionType; }
    public String getReferenceType() { return referenceType; }
    public void setReferenceType(String referenceType) { this.referenceType = referenceType; }
    public UUID getReferenceId() { return referenceId; }
    public void setReferenceId(UUID referenceId) { this.referenceId = referenceId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public int getPreviousQuantity() { return previousQuantity; }
    public void setPreviousQuantity(int previousQuantity) { this.previousQuantity = previousQuantity; }
    public int getNewQuantity() { return newQuantity; }
    public void setNewQuantity(int newQuantity) { this.newQuantity = newQuantity; }
    public BigDecimal getUnitCost() { return unitCost; }
    public void setUnitCost(BigDecimal unitCost) { this.unitCost = unitCost; }
    public BigDecimal getTotalCost() { return totalCost; }
    public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }
    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public UUID getPerformedBy() { return performedBy; }
    public void setPerformedBy(UUID performedBy) { this.performedBy = performedBy; }
}
