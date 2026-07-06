package com.icmon.module.inventory.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "t_inventory_adjustment_header")
@EqualsAndHashCode(callSuper = true)
public class StockAdjustmentHeaderEntity extends GenericBusinessEntity {
    @Column(name = "adjustment_no", unique = true, nullable = false, length = 20)
    private String adjustmentNo;
    @Column(name = "adjustment_date", nullable = false)
    private LocalDateTime adjustmentDate;
    @Column(name = "adjustment_type", length = 20)
    private String adjustmentType;
    @Column(length = 50)
    private String reason;
    @Column(length = 20)
    private String status;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(name = "total_adjustment_value", precision = 15, scale = 2)
    private BigDecimal totalAdjustmentValue;
    @Column(name = "approved_by")
    private UUID approvedBy;
    @Column(name = "approved_at")
    private LocalDateTime approvedAt;
}
