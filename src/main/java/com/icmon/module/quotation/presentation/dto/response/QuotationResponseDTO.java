package com.icmon.module.quotation.presentation.dto.response;

import com.icmon.module.quotation.domain.enums.QuotationStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class QuotationResponseDTO {
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
    private String currency;
    private String notes;
    private LocalDateTime createdAt;
}
