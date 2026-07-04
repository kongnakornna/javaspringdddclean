package com.icmon.module.job.application.usecase;

import com.icmon.module.job.application.interfaces.JobService;
import com.icmon.module.job.presentation.dto.request.JobServiceRequestDTO;
import com.icmon.module.job.presentation.dto.response.JobServiceResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AddJobServiceUseCase {
    private final JobService jobService;

    public JobServiceResponseDTO execute(UUID jobId, JobServiceRequestDTO request) throws SystemGlobalException {
        return jobService.addService(jobId, request);
    }
}
