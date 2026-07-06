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
@Table(name = "t_inventory_layer")
@EqualsAndHashCode(callSuper = true)
public class InventoryLayerEntity extends GenericBusinessEntity {
    @Column(name = "part_id", nullable = false)
    private UUID partId;
    @Column(nullable = false)
    private Integer quantity;
    @Column(name = "unit_cost", precision = 15, scale = 2)
    private BigDecimal unitCost;
    @Column(name = "received_date", nullable = false)
    private LocalDateTime receivedDate;
    @Column(name = "reference_type", length = 50)
    private String referenceType;
    @Column(name = "reference_id")
    private UUID referenceId;
    @Column(name = "is_active")
    private Boolean isActive;
}
