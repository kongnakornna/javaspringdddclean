package com.icmon.module.inventory.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_inventory_adjustment_header")
@Getter
@Setter
public class InventoryAdjustmentHeaderEntity extends GenericBusinessEntity {

    @Column(name = "adjustment_no", unique = true, nullable = false, length = 20)
    private String adjustmentNo;

    @Column(name = "adjustment_date", nullable = false)
    private LocalDateTime adjustmentDate;

    @Column(name = "adjustment_type", nullable = false, length = 20)
    private String adjustmentType;

    @Column(nullable = false, length = 50)
    private String reason;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "approved_by")
    private UUID approvedBy;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "total_adjustment_value", precision = 15, scale = 2)
    private BigDecimal totalAdjustmentValue;
}
