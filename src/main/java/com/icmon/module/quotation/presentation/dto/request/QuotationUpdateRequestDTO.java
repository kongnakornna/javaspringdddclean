package com.icmon.module.quotation.presentation.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class QuotationUpdateRequestDTO {
    private LocalDateTime expiryDate;
    private BigDecimal taxRate;
    private String discountType;
    private BigDecimal discountValue;
    private String currency;
    private BigDecimal exchangeRate;
    private String notes;
    private String termsAndConditions;
}
