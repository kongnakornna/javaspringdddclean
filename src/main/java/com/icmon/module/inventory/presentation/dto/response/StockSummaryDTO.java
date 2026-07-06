package com.icmon.module.inventory.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockSummaryDTO {
    private UUID partId;
    private String partCode;
    private String partName;
    private Integer stockQuantity;
    private Integer reorderLevel;
    private boolean lowStock;
}
