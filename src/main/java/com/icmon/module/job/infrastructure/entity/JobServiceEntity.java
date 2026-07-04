package com.icmon.module.job.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "t_job_service")
@EqualsAndHashCode(callSuper = true)
public class JobServiceEntity extends GenericBusinessEntity {

    @Column(name = "job_id", nullable = false)
    private UUID jobId;

    @Column(name = "service_id", nullable = false)
    private UUID serviceId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal unitPrice;

    @Column(precision = 15, scale = 2)
    private BigDecimal discount;

    @Column(columnDefinition = "TEXT")
    private String note;
}
