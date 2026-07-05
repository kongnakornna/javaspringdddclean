package com.icmon.module.inventory.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import java.math.BigDecimal;
import java.util.UUID;

public class InventoryAdjustmentDetail extends GenericBusinessClass {
    private UUID adjustmentHeaderId;
    private UUID partId;
    private int quantity;
    private BigDecimal unitCost;
    private BigDecimal totalCost;
    private String note;

    public UUID getAdjustmentHeaderId() { return adjustmentHeaderId; }
    public void setAdjustmentHeaderId(UUID adjustmentHeaderId) { this.adjustmentHeaderId = adjustmentHeaderId; }
    public UUID getPartId() { return partId; }
    public void setPartId(UUID partId) { this.partId = partId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getUnitCost() { return unitCost; }
    public void setUnitCost(BigDecimal unitCost) { this.unitCost = unitCost; }
    public BigDecimal getTotalCost() { return totalCost; }
    public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
