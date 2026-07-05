package com.icmon.module.purchase.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderResponseDTO {
    private UUID id;
    private String poNo;
    private UUID quotationId;
    private UUID jobId;
    private UUID supplierId;
    private LocalDateTime poDate;
    private LocalDateTime expectedDeliveryDate;
    private LocalDateTime actualDeliveryDate;
    private String status;
    private BigDecimal subtotal;
    private BigDecimal taxRate;
    private BigDecimal taxAmount;
    private String discountType;
    private BigDecimal discountValue;
    private BigDecimal total;
    private String currency;
    private BigDecimal exchangeRate;
    private BigDecimal shippingCost;
    private String paymentTerms;
    private String deliveryAddress;
    private String notes;
    private String termsAndConditions;
    private LocalDateTime sentAt;
    private LocalDateTime confirmedAt;
    private UUID receivedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
