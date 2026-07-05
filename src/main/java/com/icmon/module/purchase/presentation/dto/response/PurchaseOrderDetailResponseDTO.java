package com.icmon.module.purchase.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDetailResponseDTO {
    private UUID id;
    private UUID poHeaderId;
    private UUID partId;
    private Integer quantityOrdered;
    private Integer quantityReceived;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private BigDecimal discount;
    private BigDecimal netPrice;
    private String note;
}
