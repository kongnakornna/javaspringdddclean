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
@Table(name = "m_sales_price")
@EqualsAndHashCode(callSuper = true)
public class SalesPriceEntity extends GenericBusinessEntity {

    @Column(name = "item_id", nullable = false)
    private UUID itemId;

    @Column(name = "price_tier", length = 30)
    private String priceTier;

    @Column(name = "unit_price", precision = 15, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(length = 10)
    private String currency;

    @Column(name = "effective_date", nullable = false)
    private LocalDateTime effectiveDate;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Column(name = "min_quantity")
    private Integer minQuantity;

    @Column(name = "is_active")
    private Boolean isActive;
}
