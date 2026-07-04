package com.icmon.module.job.application.interfaces;

import com.icmon.module.job.domain.enums.JobStatus;
import com.icmon.module.job.presentation.dto.response.JobStatusHistoryDTO;
import com.icmon.exception.SystemGlobalException;

import java.util.List;
import java.util.UUID;

public interface JobStatusService {
    void changeStatus(UUID jobId, JobStatus newStatus, String reason, UUID changedBy) throws SystemGlobalException;
    List<JobStatusHistoryDTO> getStatusHistory(UUID jobId) throws SystemGlobalException;
}
