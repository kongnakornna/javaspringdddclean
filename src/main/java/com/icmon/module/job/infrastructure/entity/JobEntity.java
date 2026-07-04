package com.icmon.module.job.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import com.icmon.module.job.domain.enums.JobStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "t_job")
@EqualsAndHashCode(callSuper = true)
public class JobEntity extends GenericBusinessEntity {

    @Column(name = "job_no", nullable = false, unique = true)
    private String jobNo;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "car_id", nullable = false)
    private UUID carId;

    @Column(name = "mechanic_id", nullable = false)
    private UUID mechanicId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(columnDefinition = "TEXT")
    private String symptom;

    @Column(name = "diagnosis_note", columnDefinition = "TEXT")
    private String diagnosisNote;

    private Integer mileage;

    @Column(name = "estimated_cost", precision = 15, scale = 2)
    private BigDecimal estimatedCost;

    @Column(name = "actual_cost", precision = 15, scale = 2)
    private BigDecimal actualCost;

    @Column(length = 20)
    private String priority;
}
