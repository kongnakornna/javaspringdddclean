package com.icmon.module.job.presentation.dto.request;

import com.icmon.module.job.domain.enums.JobStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JobStatusChangeRequestDTO {

    @NotNull(message = "New status is required")
    private JobStatus newStatus;

    private String reason;
}
