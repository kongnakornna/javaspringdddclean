package com.icmon.module.inventory.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AdjustmentRequestDTO {
    @NotNull
    private UUID partId;
    @NotNull
    private Integer quantity;
    private BigDecimal unitCost;
    @NotNull
    private String reason;
    private String description;
}
