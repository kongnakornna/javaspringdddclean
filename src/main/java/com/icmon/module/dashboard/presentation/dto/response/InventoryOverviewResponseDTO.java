package com.icmon.module.dashboard.presentation.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class InventoryOverviewResponseDTO {
    private Long totalParts;
    private Long totalQuantity;
    private BigDecimal totalValue;
    private Long lowStockCount;
    private Long activeParts;
}
