package com.icmon.module.inventory.presentation.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class AdjustmentCreateRequestDTO {
    private String adjustmentType;
    private String reason;
    private String description;
    private List<AdjustmentDetailItem> details;

    @Data
    public static class AdjustmentDetailItem {
        private String partId;
        private int quantity;
        private String note;
    }
}
