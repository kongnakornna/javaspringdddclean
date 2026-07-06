package com.icmon.module.inventory.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class TInventoryAlertHistory extends GenericBusinessClass {
    private LocalDate alertDate;
    private UUID partId;
    private String partCode;
    private String partName;
    private Integer currentStock;
    private Integer reorderLevel;
    private Integer reorderQuantity;
    private Boolean alertSent;
    private LocalDateTime alertSentAt;
    private Boolean resolved;
    private LocalDateTime resolvedAt;
    private String note;
}
