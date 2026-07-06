package com.icmon.module.inventory.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.inventory.domain.enums.TransactionType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class TInventory extends GenericBusinessClass {
    private UUID partId;
    private TransactionType transactionType;
    private String referenceType;
    private UUID referenceId;
    private Integer quantity;
    private Integer previousQuantity;
    private Integer newQuantity;
    private BigDecimal unitCost;
    private BigDecimal totalCost;
    private LocalDateTime transactionDate;
    private String note;
    private UUID performedBy;
}
