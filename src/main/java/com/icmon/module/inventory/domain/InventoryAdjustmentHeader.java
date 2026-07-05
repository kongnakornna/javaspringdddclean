package com.icmon.module.inventory.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.inventory.domain.enums.AdjustmentReason;
import com.icmon.module.inventory.domain.enums.AdjustmentType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class InventoryAdjustmentHeader extends GenericBusinessClass {
    private String adjustmentNo;
    private LocalDateTime adjustmentDate;
    private AdjustmentType adjustmentType;
    private AdjustmentReason reason;
    private String status;
    private String description;
    private UUID approvedBy;
    private LocalDateTime approvedAt;
    private BigDecimal totalAdjustmentValue;

    public String getAdjustmentNo() { return adjustmentNo; }
    public void setAdjustmentNo(String adjustmentNo) { this.adjustmentNo = adjustmentNo; }
    public LocalDateTime getAdjustmentDate() { return adjustmentDate; }
    public void setAdjustmentDate(LocalDateTime adjustmentDate) { this.adjustmentDate = adjustmentDate; }
    public AdjustmentType getAdjustmentType() { return adjustmentType; }
    public void setAdjustmentType(AdjustmentType adjustmentType) { this.adjustmentType = adjustmentType; }
    public AdjustmentReason getReason() { return reason; }
    public void setReason(AdjustmentReason reason) { this.reason = reason; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public UUID getApprovedBy() { return approvedBy; }
    public void setApprovedBy(UUID approvedBy) { this.approvedBy = approvedBy; }
    public LocalDateTime getApprovedAt() { return approvedAt; }
    public void setApprovedAt(LocalDateTime approvedAt) { this.approvedAt = approvedAt; }
    public BigDecimal getTotalAdjustmentValue() { return totalAdjustmentValue; }
    public void setTotalAdjustmentValue(BigDecimal totalAdjustmentValue) { this.totalAdjustmentValue = totalAdjustmentValue; }
}
