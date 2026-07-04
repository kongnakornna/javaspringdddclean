package com.icmon.module.job.application.usecase;

import com.icmon.module.job.application.interfaces.JobService;
import com.icmon.module.job.presentation.dto.request.JobPartRequestDTO;
import com.icmon.module.job.presentation.dto.response.JobPartResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AddJobPartUseCase {
    private final JobService jobService;

    public JobPartResponseDTO execute(UUID jobId, JobPartRequestDTO request) throws SystemGlobalException {
        return jobService.addPart(jobId, request);
    }
}
