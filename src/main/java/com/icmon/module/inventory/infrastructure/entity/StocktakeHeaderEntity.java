package com.icmon.module.inventory.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "t_stocktake_header")
@EqualsAndHashCode(callSuper = true)
public class StockTakeHeaderEntity extends GenericBusinessEntity {
    @Column(name = "stocktake_no", unique = true, nullable = false, length = 20)
    private String stocktakeNo;
    @Column(name = "stocktake_date", nullable = false)
    private LocalDateTime stocktakeDate;
    @Column(length = 20)
    private String status;
    @Column(name = "started_by")
    private UUID startedBy;
    @Column(name = "started_at")
    private LocalDateTime startedAt;
    @Column(name = "completed_by")
    private UUID completedBy;
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
    @Column(columnDefinition = "TEXT")
    private String notes;
}
