package com.icmon.module.quotation.presentation.dto.response;

import com.icmon.module.quotation.domain.enums.QuotationStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class QuotationDetailResponseDTO {
    private UUID id;
    private String quotationNo;
    private UUID jobId;
    private UUID customerId;
    private LocalDateTime quotationDate;
    private LocalDateTime expiryDate;
    private QuotationStatus status;
    private BigDecimal subtotal;
    private BigDecimal taxRate;
    private BigDecimal taxAmount;
    private String discountType;
    private BigDecimal discountValue;
    private BigDecimal total;
    private String amountInWordsTh;
    private String amountInWordsEn;
    private String currency;
    private BigDecimal exchangeRate;
    private String notes;
    private String termsAndConditions;
    private UUID approvedBy;
    private LocalDateTime approvedAt;
    private String rejectedReason;
    private Boolean convertedToPo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
