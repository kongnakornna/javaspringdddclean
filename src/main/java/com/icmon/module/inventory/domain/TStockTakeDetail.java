package com.icmon.module.inventory.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class TStockTakeDetail extends GenericBusinessClass {
    private UUID stocktakeHeaderId;
    private UUID partId;
    private Integer systemQuantity;
    private Integer countedQuantity;
    private Integer discrepancy;
    private String note;
    private UUID countedBy;
    private LocalDateTime countedAt;
}
