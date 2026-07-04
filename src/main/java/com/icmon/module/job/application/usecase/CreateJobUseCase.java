package com.icmon.module.job.application.usecase;

import com.icmon.module.job.application.interfaces.JobService;
import com.icmon.module.job.presentation.dto.request.JobCreateRequestDTO;
import com.icmon.module.job.presentation.dto.response.JobResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateJobUseCase {
    private final JobService jobService;

    public JobResponseDTO execute(JobCreateRequestDTO request) throws SystemGlobalException {
        return jobService.createJob(request);
    }
}
