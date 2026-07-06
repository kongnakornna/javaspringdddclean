package com.icmon.module.document.presentation.dto.response.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Schema(description = "\u0e02\u0e49\u0e2d\u0e21\u0e39\u0e25\u0e2a\u0e33\u0e2b\u0e23\u0e31\u0e1a\u0e23\u0e32\u0e22\u0e07\u0e32\u0e19\u0e2a\u0e34\u0e19\u0e04\u0e49\u0e32\u0e04\u0e07\u0e04\u0e25\u0e31\u0e07 // Inventory Summary report data")
public class InventorySummaryReportDTO {
    private String companyName;
    private String reportDate;
    private Integer totalItems;
    private BigDecimal totalValue;
    private Integer lowStockCount;
    private List<InventoryItemDTO> items;

    @Data @Builder
    public static class InventoryItemDTO {
        private String partCode;
        private String partName;
        private String category;
        private Integer stockQty;
        private BigDecimal unitCost;
        private BigDecimal totalValue;
        private Integer minStock;
        private String location;
    }
}
