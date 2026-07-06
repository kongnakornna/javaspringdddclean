package com.icmon.module.document.presentation.dto.response.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Schema(description = "\u0e02\u0e49\u0e2d\u0e21\u0e39\u0e25\u0e2a\u0e33\u0e2b\u0e23\u0e31\u0e1a\u0e23\u0e32\u0e22\u0e07\u0e32\u0e19\u0e22\u0e2d\u0e14\u0e02\u0e32\u0e22\u0e23\u0e32\u0e22\u0e27\u0e31\u0e19 // Daily Sales report data")
public class DailySalesReportDTO {
    private String companyName;
    private String reportDate;
    private BigDecimal totalRevenue;
    private Integer totalInvoices;
    private Integer totalCustomers;
    private BigDecimal avgPerInvoice;
    private List<SalesItemDTO> items;

    @Data @Builder
    public static class SalesItemDTO {
        private String customerName;
        private String invoiceNo;
        private BigDecimal amount;
    }
}
