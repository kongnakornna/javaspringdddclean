package com.icmon.module.weborder.domain;

import com.icmon._shared.domain.GenericClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class TWebOrderItem extends GenericClass {

    private UUID orderId;
    private UUID itemId;
    private UUID partId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private BigDecimal discount;
    private BigDecimal netPrice;
    private String attributes;
}
