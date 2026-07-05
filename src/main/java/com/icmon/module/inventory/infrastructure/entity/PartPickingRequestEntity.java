package com.icmon.module.inventory.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_part_picking_request")
@Getter
@Setter
public class PartPickingRequestEntity extends GenericBusinessEntity {

    @Column(name = "picking_no", unique = true, nullable = false, length = 20)
    private String pickingNo;

    @Column(name = "job_id")
    private UUID jobId;

    @Column(name = "quotation_id")
    private UUID quotationId;

    @Column(name = "requested_date", nullable = false)
    private LocalDateTime requestedDate;

    @Column(name = "requested_by", nullable = false)
    private UUID requestedBy;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(length = 20)
    private String priority;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "picked_by")
    private UUID pickedBy;

    @Column(name = "picked_date")
    private LocalDateTime pickedDate;

    @Column(name = "confirmed_by")
    private UUID confirmedBy;

    @Column(name = "confirmed_date")
    private LocalDateTime confirmedDate;
}
