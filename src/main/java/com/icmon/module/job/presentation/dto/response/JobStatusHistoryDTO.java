package com.icmon.module.job.presentation.dto.response;

import com.icmon.module.job.domain.enums.JobStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class JobStatusHistoryDTO {
    private UUID id;
    private UUID jobId;
    private JobStatus fromStatus;
    private JobStatus toStatus;
    private UUID changedBy;
    private LocalDateTime changedAt;
    private String reason;
}
