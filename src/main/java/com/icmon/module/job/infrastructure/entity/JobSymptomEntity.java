package com.icmon.module.job.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@Entity
@Table(name = "t_job_service_car_symptom")
@EqualsAndHashCode(callSuper = true)
public class JobSymptomEntity extends GenericBusinessEntity {

    @Column(name = "job_id", nullable = false)
    private UUID jobId;

    @Column(name = "symptom_code", length = 20)
    private String symptomCode;

    @Column(name = "symptom_description", nullable = false, columnDefinition = "TEXT")
    private String symptomDescription;

    @Column(length = 20)
    private String severity;

    @Column(name = "reported_by", length = 100)
    private String reportedBy;
}
