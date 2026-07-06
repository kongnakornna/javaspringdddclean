package com.icmon.module.inventory.presentation.dto.request;

import lombok.Data;
import java.util.UUID;

@Data
public class StockTakeRequestDTO {
    private String notes;
    private UUID partId;
    private Integer countedQuantity;
}
