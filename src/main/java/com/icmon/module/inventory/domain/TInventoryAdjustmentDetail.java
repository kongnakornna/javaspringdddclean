package com.icmon.module.inventory.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class TInventoryAdjustmentDetail extends GenericBusinessClass {
    private UUID adjustmentHeaderId;
    private UUID partId;
    private Integer quantity;
    private BigDecimal unitCost;
    private BigDecimal totalCost;
    private String note;
}
