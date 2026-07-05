package com.icmon.module.inventory.presentation.dto.request;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PickingCreateRequestDTO {
    private UUID jobId;
    private UUID quotationId;
    private UUID requestedBy;
    private String priority;
    private String notes;
    private List<PickingItem> items;

    @Data
    public static class PickingItem {
        private UUID partId;
        private int requestedQuantity;
        private String note;
    }
}
