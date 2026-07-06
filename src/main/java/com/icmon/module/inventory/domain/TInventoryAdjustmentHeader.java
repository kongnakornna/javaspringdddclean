package com.icmon.module.inventory.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class TInventoryAdjustmentHeader extends GenericBusinessClass {
    private String adjustmentNo;
    private LocalDateTime adjustmentDate;
    private String adjustmentType;
    private String reason;
    private String status;
    private String description;
    private UUID approvedBy;
    private LocalDateTime approvedAt;
    private BigDecimal totalAdjustmentValue;
}
