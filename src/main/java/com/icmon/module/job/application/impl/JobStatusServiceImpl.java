package com.icmon.module.job.application.impl;

import com.icmon.module.job.application.interfaces.JobStatusService;
import com.icmon.module.job.domain.enums.JobStatus;
import com.icmon.module.job.infrastructure.entity.JobStatusHistoryEntity;
import com.icmon.module.job.infrastructure.repository.JobStatusHistoryRepository;
import com.icmon.module.job.presentation.dto.response.JobStatusHistoryDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobStatusServiceImpl implements JobStatusService {

    private final JobStatusHistoryRepository jobStatusHistoryRepository;

    @Override
    public void changeStatus(UUID jobId, JobStatus newStatus, String reason, UUID changedBy) throws SystemGlobalException {
        JobStatusHistoryEntity history = new JobStatusHistoryEntity();
        history.setJobId(jobId);
        history.setToStatus(newStatus);
        history.setChangedBy(changedBy);
        history.setChangedAt(LocalDateTime.now());
        history.setReason(reason);
        jobStatusHistoryRepository.save(history);
    }

    @Override
    public List<JobStatusHistoryDTO> getStatusHistory(UUID jobId) throws SystemGlobalException {
        return jobStatusHistoryRepository.findByJobIdOrderByChangedAtAsc(jobId)
                .stream()
                .map(h -> JobStatusHistoryDTO.builder()
                        .id(h.getId())
                        .jobId(h.getJobId())
                        .fromStatus(h.getFromStatus())
                        .toStatus(h.getToStatus())
                        .changedBy(h.getChangedBy())
                        .changedAt(h.getChangedAt())
                        .reason(h.getReason())
                        .build())
                .collect(Collectors.toList());
    }
}
