package com.icmon.module.inventory.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class TStockTakeHeader extends GenericBusinessClass {
    private String stocktakeNo;
    private LocalDateTime stocktakeDate;
    private String status;
    private UUID startedBy;
    private LocalDateTime startedAt;
    private UUID completedBy;
    private LocalDateTime completedAt;
    private Integer totalDiscrepancy;
    private String notes;
}
