package com.icmon.module.inventory.presentation.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class InventoryReceiveRequestDTO {
    @NotNull
    private UUID partId;
    @NotNull @Min(1)
    private Integer quantity;
    @NotNull
    private BigDecimal unitCost;
    private String referenceType;
    private UUID referenceId;
    private String note;
}
