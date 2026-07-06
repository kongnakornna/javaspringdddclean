package com.icmon.module.document.presentation.dto.response.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "\u0e02\u0e49\u0e2d\u0e21\u0e39\u0e25\u0e2a\u0e33\u0e2b\u0e23\u0e31\u0e1a\u0e23\u0e32\u0e22\u0e07\u0e32\u0e19\u0e43\u0e1a\u0e2a\u0e48\u0e07\u0e02\u0e2d\u0e07 // Delivery Sheet report data")
public class DeliverySheetReportDTO {
    private String companyName;
    private String deliveryNo;
    private String deliveryDate;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private String referenceNo;
    private String deliveryPerson;
    private String remark;
    private List<DeliveryItemDTO> items;

    @Data @Builder
    public static class DeliveryItemDTO {
        private Integer lineNo;
        private String description;
        private Integer quantity;
        private String unit;
        private String note;
    }
}
