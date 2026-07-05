package com.icmon.module.inventory.presentation.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class InventoryResponseDTO {
    private UUID id;
    private UUID partId;
    private String transactionType;
    private String referenceType;
    private UUID referenceId;
    private int quantity;
    private int previousQuantity;
    private int newQuantity;
    private BigDecimal unitCost;
    private BigDecimal totalCost;
    private LocalDateTime transactionDate;
    private String note;
    private UUID performedBy;
    private LocalDateTime createdAt;
}
