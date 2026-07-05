package com.icmon.module.inventory.presentation.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class StocktakeResponseDTO {
    private UUID id;
    private String stocktakeNo;
    private LocalDateTime stocktakeDate;
    private String status;
    private UUID startedBy;
    private LocalDateTime startedAt;
    private UUID completedBy;
    private LocalDateTime completedAt;
    private int totalDiscrepancy;
    private String notes;
    private List<StocktakeDetailItem> details;
    private LocalDateTime createdAt;

    @Data
    public static class StocktakeDetailItem {
        private UUID id;
        private UUID partId;
        private int systemQuantity;
        private int countedQuantity;
        private int discrepancy;
        private String note;
        private UUID countedBy;
        private LocalDateTime countedAt;
    }
}
