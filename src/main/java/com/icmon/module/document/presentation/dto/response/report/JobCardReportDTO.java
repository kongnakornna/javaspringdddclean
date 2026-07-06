package com.icmon.module.document.presentation.dto.response.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Schema(description = "\u0e02\u0e49\u0e2d\u0e21\u0e39\u0e25\u0e2a\u0e33\u0e2b\u0e23\u0e31\u0e1a\u0e23\u0e32\u0e22\u0e07\u0e32\u0e19\u0e43\u0e1a\u0e07\u0e32\u0e19\u0e0b\u0e48\u0e2d\u0e21 // Job Card report data")
public class JobCardReportDTO {
    private String companyName;
    private String jobNo;
    private String jobDate;
    private String customerName;
    private String customerPhone;
    private String licensePlate;
    private String carModel;
    private String carColor;
    private String mileage;
    private String mechanicName;
    private String serviceAdvisor;
    private String complaint;
    private String remark;
    private BigDecimal totalParts;
    private BigDecimal totalLabor;
    private BigDecimal grandTotal;
    private List<JobCardItemDTO> items;

    @Data @Builder
    public static class JobCardItemDTO {
        private String type;
        private String description;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
    }
}
