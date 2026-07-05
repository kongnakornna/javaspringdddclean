package com.icmon.module.inventory.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import java.math.BigDecimal;
import java.util.UUID;

public class PartPickingDetail extends GenericBusinessClass {
    private UUID pickingRequestId;
    private UUID partId;
    private int requestedQuantity;
    private int pickedQuantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String note;

    public UUID getPickingRequestId() { return pickingRequestId; }
    public void setPickingRequestId(UUID pickingRequestId) { this.pickingRequestId = pickingRequestId; }
    public UUID getPartId() { return partId; }
    public void setPartId(UUID partId) { this.partId = partId; }
    public int getRequestedQuantity() { return requestedQuantity; }
    public void setRequestedQuantity(int requestedQuantity) { this.requestedQuantity = requestedQuantity; }
    public int getPickedQuantity() { return pickedQuantity; }
    public void setPickedQuantity(int pickedQuantity) { this.pickedQuantity = pickedQuantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
