package com.icmon.module.inventory.presentation.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class InventoryTransactionRequestDTO {
    private UUID partId;
    private String transactionType;
    private String referenceType;
    private UUID referenceId;
    private int quantity;
    private BigDecimal unitCost;
    private String note;
    private UUID performedBy;
}
