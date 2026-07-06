package com.icmon.module.weborder.domain;

import com.icmon._shared.domain.GenericClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class TShoppingCartItem extends GenericClass {

    private UUID cartId;
    private UUID itemId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String attributes;
}
