package com.icmon.module.weborder.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "t_web_order_item")
@EqualsAndHashCode(callSuper = true)
public class WebOrderItemEntity extends GenericEntity {

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "item_id", nullable = false)
    private UUID itemId;

    @Column(name = "part_id")
    private UUID partId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", precision = 15, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "total_price", precision = 15, scale = 2)
    private BigDecimal totalPrice;

    @Column(precision = 15, scale = 2)
    private BigDecimal discount;

    @Column(name = "net_price", precision = 15, scale = 2)
    private BigDecimal netPrice;

    @Column(columnDefinition = "JSONB")
    private String attributes;
}
