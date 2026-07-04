package com.icmon.module.job.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@Entity
@Table(name = "t_job_diag_trouble_code")
@EqualsAndHashCode(callSuper = true)
public class JobDiagCodeEntity extends GenericBusinessEntity {

    @Column(name = "job_id", nullable = false)
    private UUID jobId;

    @Column(name = "trouble_code", nullable = false, length = 20)
    private String troubleCode;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 50)
    private String system;
}
