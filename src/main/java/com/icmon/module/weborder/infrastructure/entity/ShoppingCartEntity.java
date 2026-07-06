package com.icmon.module.weborder.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "t_shopping_cart")
@EqualsAndHashCode(callSuper = true)
public class ShoppingCartEntity extends GenericBusinessEntity {

    @Column(name = "cart_id", unique = true, nullable = false, length = 50)
    private String cartId;

    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(precision = 15, scale = 2)
    private BigDecimal subtotal;

    @Column(precision = 15, scale = 2)
    private BigDecimal discount;

    @Column(precision = 15, scale = 2)
    private BigDecimal tax;

    @Column(precision = 15, scale = 2)
    private BigDecimal shipping;

    @Column(precision = 15, scale = 2)
    private BigDecimal total;

    @Column(name = "promotion_code", length = 50)
    private String promotionCode;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
