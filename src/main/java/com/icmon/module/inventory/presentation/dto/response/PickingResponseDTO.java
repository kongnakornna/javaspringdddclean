package com.icmon.module.inventory.presentation.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class PickingResponseDTO {
    private UUID id;
    private String pickingNo;
    private UUID jobId;
    private UUID quotationId;
    private LocalDateTime requestedDate;
    private UUID requestedBy;
    private String status;
    private String priority;
    private String notes;
    private UUID pickedBy;
    private LocalDateTime pickedDate;
    private UUID confirmedBy;
    private LocalDateTime confirmedDate;
    private List<PickingItem> items;
    private LocalDateTime createdAt;

    @Data
    public static class PickingItem {
        private UUID id;
        private UUID partId;
        private int requestedQuantity;
        private int pickedQuantity;
        private String note;
    }
}
