package com.icmon.module.purchase.presentation.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PurchaseOrderUpdateRequestDTO {
    private UUID supplierId;
    private LocalDateTime expectedDeliveryDate;
    private BigDecimal taxRate;
    private String discountType;
    private BigDecimal discountValue;
    private String currency;
    private BigDecimal exchangeRate;
    private BigDecimal shippingCost;
    private String paymentTerms;
    private String deliveryAddress;
    private String notes;
    private String termsAndConditions;
}
