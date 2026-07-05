package com.icmon.module.inventory.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_stocktake_detail")
@Getter
@Setter
public class StocktakeDetailEntity extends GenericBusinessEntity {

    @Column(name = "stocktake_header_id", nullable = false)
    private UUID stocktakeHeaderId;

    @Column(name = "part_id", nullable = false)
    private UUID partId;

    @Column(name = "system_quantity", nullable = false)
    private int systemQuantity;

    @Column(name = "counted_quantity", nullable = false)
    private int countedQuantity;

    @Column(nullable = false)
    private int discrepancy;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(name = "counted_by")
    private UUID countedBy;

    @Column(name = "counted_at")
    private LocalDateTime countedAt;
}
