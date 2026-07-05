package com.icmon.module.purchase.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PurchaseOrderCreateRequestDTO {
    private UUID quotationId;
    private UUID jobId;

    @NotNull(message = "Supplier ID is required")
    private UUID supplierId;

    private LocalDateTime expectedDeliveryDate;
    private BigDecimal taxRate;
    private String currency;
    private BigDecimal exchangeRate;
    private String paymentTerms;
    private String deliveryAddress;
    private String notes;
    private String termsAndConditions;
}
