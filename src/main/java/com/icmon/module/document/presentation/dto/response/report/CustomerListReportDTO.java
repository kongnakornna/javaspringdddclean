package com.icmon.module.document.presentation.dto.response.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "\u0e02\u0e49\u0e2d\u0e21\u0e39\u0e25\u0e2a\u0e33\u0e2b\u0e23\u0e31\u0e1a\u0e23\u0e32\u0e22\u0e07\u0e32\u0e19\u0e23\u0e32\u0e22\u0e0a\u0e37\u0e48\u0e2d\u0e25\u0e39\u0e01\u0e04\u0e49\u0e32 // Customer List report data")
public class CustomerListReportDTO {
    private String companyName;
    private String reportDate;
    private Integer totalCustomers;
    private List<CustomerItemDTO> items;

    @Data @Builder
    public static class CustomerItemDTO {
        private String customerCode;
        private String customerName;
        private String customerPhone;
        private String customerEmail;
        private String customerType;
        private String registerDate;
    }
}
