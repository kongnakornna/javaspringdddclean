package com.icmon.module.quotation.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class QuotationCreateRequestDTO {
    @NotNull(message = "Job ID is required")
    private UUID jobId;

    @NotNull(message = "Customer ID is required")
    private UUID customerId;

    private LocalDateTime expiryDate;

    private BigDecimal taxRate;

    private String currency;

    private BigDecimal exchangeRate;

    private String notes;

    private String termsAndConditions;
}
