package com.icmon.module.purchase.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.purchase.domain.enums.PurchaseOrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TPurchaseOrderStatusHistory extends GenericBusinessClass {

    private UUID poHeaderId;
    private PurchaseOrderStatus fromStatus;
    private PurchaseOrderStatus toStatus;
    private UUID changedBy;
    private LocalDateTime changedAt;
    private String reason;
}
