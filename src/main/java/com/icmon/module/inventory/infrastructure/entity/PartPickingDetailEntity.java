package com.icmon.module.inventory.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "t_part_picking_detail")
@EqualsAndHashCode(callSuper = true)
public class PartPickingDetailEntity extends GenericBusinessEntity {
    @Column(name = "picking_request_id", nullable = false)
    private UUID pickingRequestId;
    @Column(name = "part_id", nullable = false)
    private UUID partId;
    @Column(name = "requested_quantity", nullable = false)
    private Integer requestedQuantity;
    @Column(name = "picked_quantity")
    private Integer pickedQuantity;
    @Column(name = "unit_price", precision = 15, scale = 2)
    private BigDecimal unitPrice;
    @Column(name = "total_price", precision = 15, scale = 2)
    private BigDecimal totalPrice;
    @Column(columnDefinition = "TEXT")
    private String note;
}
