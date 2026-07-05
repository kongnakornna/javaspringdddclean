package com.icmon.module.inventory.presentation.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class AdjustmentResponseDTO {
    private UUID id;
    private String adjustmentNo;
    private LocalDateTime adjustmentDate;
    private String adjustmentType;
    private String reason;
    private String status;
    private String description;
    private UUID approvedBy;
    private LocalDateTime approvedAt;
    private BigDecimal totalAdjustmentValue;
    private List<AdjustmentDetailItem> details;
    private LocalDateTime createdAt;

    @Data
    public static class AdjustmentDetailItem {
        private UUID id;
        private UUID partId;
        private int quantity;
        private String note;
    }
}
