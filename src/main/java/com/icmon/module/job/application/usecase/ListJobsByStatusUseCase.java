package com.icmon.module.job.application.usecase;

import com.icmon.module.job.application.interfaces.JobService;
import com.icmon.module.job.presentation.dto.response.JobResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListJobsByStatusUseCase {
    private final JobService jobService;

    public Page<JobResponseDTO> execute(String status, String startDate, String endDate, Pageable pageable) throws SystemGlobalException {
        return jobService.listJobs(status, startDate, endDate, pageable);
    }
}
