package com.icmon.module.job.domain;

import com.icmon.module.job.domain.enums.JobStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TJobStatusHistory {

    private UUID id;
    private UUID jobId;
    private JobStatus fromStatus;
    private JobStatus toStatus;
    private UUID changedBy;
    private LocalDateTime changedAt;
    private String reason;
    private UUID whitelabelId;
}
