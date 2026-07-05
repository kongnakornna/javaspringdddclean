package com.icmon.module.purchase.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "t_purchase_order_detail")
@Getter
@Setter
public class PurchaseOrderDetailEntity extends GenericBusinessEntity {

    @Column(name = "po_header_id", nullable = false)
    private UUID poHeaderId;

    @Column(name = "part_id", nullable = false)
    private UUID partId;

    @Column(name = "quantity_ordered", nullable = false)
    private Integer quantityOrdered;

    @Column(name = "quantity_received")
    private Integer quantityReceived;

    @Column(name = "unit_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "total_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalPrice;

    @Column(precision = 15, scale = 2)
    private BigDecimal discount;

    @Column(name = "net_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal netPrice;

    @Column(columnDefinition = "TEXT")
    private String note;
}
