package com.icmon.module.payment.presentation.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentMethodResponseDTO {
    private UUID id;
    private String methodCode;
    private String methodName;
    private String methodNameEn;
    private boolean isActive;
    private boolean requiresApproval;
    private BigDecimal feePercentage;
    private BigDecimal feeFixed;
    private String description;
}
