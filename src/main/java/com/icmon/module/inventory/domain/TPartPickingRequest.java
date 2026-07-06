package com.icmon.module.inventory.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.inventory.domain.enums.PickingStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class TPartPickingRequest extends GenericBusinessClass {
    private String pickingNo;
    private UUID jobId;
    private UUID quotationId;
    private LocalDateTime requestedDate;
    private UUID requestedBy;
    private PickingStatus status;
    private String priority;
    private String notes;
    private UUID pickedBy;
    private LocalDateTime pickedDate;
    private UUID confirmedBy;
    private LocalDateTime confirmedDate;
    private List<TPartPickingDetail> details = new ArrayList<>();

    public void confirm(UUID userId) {
        if (this.status == PickingStatus.CANCELLED) {
            throw new IllegalStateException("Cannot confirm a cancelled picking request.");
        }
        this.status = PickingStatus.CONFIRMED;
        this.confirmedBy = userId;
        this.confirmedDate = LocalDateTime.now();
    }

    public void cancel(String reason) {
        if (this.status == PickingStatus.CONFIRMED) {
            throw new IllegalStateException("Cannot cancel a confirmed picking request.");
        }
        this.status = PickingStatus.CANCELLED;
        this.notes = (this.notes != null ? this.notes + "\n" : "") + "Cancelled: " + reason;
    }
}
