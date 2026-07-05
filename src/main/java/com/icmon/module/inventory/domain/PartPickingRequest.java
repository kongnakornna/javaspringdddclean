package com.icmon.module.inventory.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.inventory.domain.enums.PickingPriority;
import com.icmon.module.inventory.domain.enums.PickingStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public class PartPickingRequest extends GenericBusinessClass {
    private String pickingNo;
    private UUID jobId;
    private UUID quotationId;
    private LocalDateTime requestedDate;
    private UUID requestedBy;
    private PickingStatus status;
    private PickingPriority priority;
    private String notes;
    private UUID pickedBy;
    private LocalDateTime pickedDate;
    private UUID confirmedBy;
    private LocalDateTime confirmedDate;

    public String getPickingNo() { return pickingNo; }
    public void setPickingNo(String pickingNo) { this.pickingNo = pickingNo; }
    public UUID getJobId() { return jobId; }
    public void setJobId(UUID jobId) { this.jobId = jobId; }
    public UUID getQuotationId() { return quotationId; }
    public void setQuotationId(UUID quotationId) { this.quotationId = quotationId; }
    public LocalDateTime getRequestedDate() { return requestedDate; }
    public void setRequestedDate(LocalDateTime requestedDate) { this.requestedDate = requestedDate; }
    public UUID getRequestedBy() { return requestedBy; }
    public void setRequestedBy(UUID requestedBy) { this.requestedBy = requestedBy; }
    public PickingStatus getStatus() { return status; }
    public void setStatus(PickingStatus status) { this.status = status; }
    public PickingPriority getPriority() { return priority; }
    public void setPriority(PickingPriority priority) { this.priority = priority; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public UUID getPickedBy() { return pickedBy; }
    public void setPickedBy(UUID pickedBy) { this.pickedBy = pickedBy; }
    public LocalDateTime getPickedDate() { return pickedDate; }
    public void setPickedDate(LocalDateTime pickedDate) { this.pickedDate = pickedDate; }
    public UUID getConfirmedBy() { return confirmedBy; }
    public void setConfirmedBy(UUID confirmedBy) { this.confirmedBy = confirmedBy; }
    public LocalDateTime getConfirmedDate() { return confirmedDate; }
    public void setConfirmedDate(LocalDateTime confirmedDate) { this.confirmedDate = confirmedDate; }
}
