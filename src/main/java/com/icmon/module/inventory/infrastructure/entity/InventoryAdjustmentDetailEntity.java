package com.icmon.module.inventory.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "t_inventory_adjustment_detail")
@Getter
@Setter
public class InventoryAdjustmentDetailEntity extends GenericBusinessEntity {

    @Column(name = "adjustment_header_id", nullable = false)
    private UUID adjustmentHeaderId;

    @Column(name = "part_id", nullable = false)
    private UUID partId;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "unit_cost", precision = 15, scale = 2)
    private BigDecimal unitCost;

    @Column(name = "total_cost", precision = 15, scale = 2)
    private BigDecimal totalCost;

    @Column(columnDefinition = "TEXT")
    private String note;
}
