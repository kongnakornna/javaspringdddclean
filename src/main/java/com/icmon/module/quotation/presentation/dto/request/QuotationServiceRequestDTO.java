package com.icmon.module.quotation.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class QuotationServiceRequestDTO {
    @NotNull(message = "Quotation ID is required")
    private UUID quotationId;

    @NotNull(message = "Service ID is required")
    private UUID serviceId;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotNull(message = "Unit price is required")
    private BigDecimal unitPrice;

    private BigDecimal discount;

    private String note;
}
