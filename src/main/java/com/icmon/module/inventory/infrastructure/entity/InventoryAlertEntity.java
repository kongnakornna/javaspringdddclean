package com.icmon.module.inventory.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "t_inventory_alert_history")
@EqualsAndHashCode(callSuper = true)
public class InventoryAlertEntity extends GenericBusinessEntity {
    @Column(name = "alert_date", nullable = false)
    private LocalDate alertDate;
    @Column(name = "part_id", nullable = false)
    private UUID partId;
    @Column(name = "part_code", length = 50)
    private String partCode;
    @Column(name = "part_name", length = 200)
    private String partName;
    @Column(name = "current_stock")
    private Integer currentStock;
    @Column(name = "reorder_level")
    private Integer reorderLevel;
    @Column(name = "reorder_quantity")
    private Integer reorderQuantity;
    @Column(name = "alert_sent")
    private Boolean alertSent;
    @Column
    private Boolean resolved;
}
