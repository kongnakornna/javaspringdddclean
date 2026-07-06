package com.icmon.module.inventory.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class TInventoryLayer extends GenericBusinessClass {
    private UUID partId;
    private Integer quantity;
    private BigDecimal unitCost;
    private LocalDateTime receivedDate;
    private String referenceType;
    private UUID referenceId;
    private Boolean isActive;

    public boolean isLowStock() {
        return false;
    }
}
