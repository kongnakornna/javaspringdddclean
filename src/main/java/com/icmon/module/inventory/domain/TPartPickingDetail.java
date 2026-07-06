package com.icmon.module.inventory.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class TPartPickingDetail extends GenericBusinessClass {
    private UUID pickingRequestId;
    private UUID partId;
    private Integer requestedQuantity;
    private Integer pickedQuantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String note;
}
