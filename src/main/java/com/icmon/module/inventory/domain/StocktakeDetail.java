package com.icmon.module.inventory.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import java.time.LocalDateTime;
import java.util.UUID;

public class StocktakeDetail extends GenericBusinessClass {
    private UUID stocktakeHeaderId;
    private UUID partId;
    private int systemQuantity;
    private int countedQuantity;
    private int discrepancy;
    private String note;
    private UUID countedBy;
    private LocalDateTime countedAt;

    public UUID getStocktakeHeaderId() { return stocktakeHeaderId; }
    public void setStocktakeHeaderId(UUID stocktakeHeaderId) { this.stocktakeHeaderId = stocktakeHeaderId; }
    public UUID getPartId() { return partId; }
    public void setPartId(UUID partId) { this.partId = partId; }
    public int getSystemQuantity() { return systemQuantity; }
    public void setSystemQuantity(int systemQuantity) { this.systemQuantity = systemQuantity; }
    public int getCountedQuantity() { return countedQuantity; }
    public void setCountedQuantity(int countedQuantity) { this.countedQuantity = countedQuantity; }
    public int getDiscrepancy() { return discrepancy; }
    public void setDiscrepancy(int discrepancy) { this.discrepancy = discrepancy; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public UUID getCountedBy() { return countedBy; }
    public void setCountedBy(UUID countedBy) { this.countedBy = countedBy; }
    public LocalDateTime getCountedAt() { return countedAt; }
    public void setCountedAt(LocalDateTime countedAt) { this.countedAt = countedAt; }
}
