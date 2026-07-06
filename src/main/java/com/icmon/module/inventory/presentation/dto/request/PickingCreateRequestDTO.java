package com.icmon.module.inventory.presentation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class PickingCreateRequestDTO {
    private UUID jobId;
    private UUID quotationId;
    private String priority;
    private String notes;
    @NotEmpty
    private List<PickingItemRequest> items;

    @Data
    public static class PickingItemRequest {
        private UUID partId;
        private Integer quantity;
        private BigDecimal unitPrice;
    }
}
