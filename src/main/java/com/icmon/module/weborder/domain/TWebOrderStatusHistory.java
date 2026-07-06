package com.icmon.module.weborder.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.weborder.domain.enums.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class TWebOrderStatusHistory extends GenericBusinessClass {

    private UUID orderId;
    private OrderStatus fromStatus;
    private OrderStatus toStatus;
    private UUID changedBy;
    private LocalDateTime changedAt;
    private String reason;
}
