package com.icmon.module.weborder.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "t_shopping_cart_item")
@EqualsAndHashCode(callSuper = true)
public class ShoppingCartItemEntity extends GenericEntity {

    @Column(name = "cart_id", nullable = false)
    private UUID cartId;

    @Column(name = "item_id", nullable = false)
    private UUID itemId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", precision = 15, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "total_price", precision = 15, scale = 2)
    private BigDecimal totalPrice;

    @Column(columnDefinition = "JSONB")
    private String attributes;
}
