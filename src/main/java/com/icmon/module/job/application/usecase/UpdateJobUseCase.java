package com.icmon.module.job.application.usecase;

import com.icmon.module.job.application.interfaces.JobService;
import com.icmon.module.job.presentation.dto.request.JobUpdateRequestDTO;
import com.icmon.module.job.presentation.dto.response.JobResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateJobUseCase {
    private final JobService jobService;

    public JobResponseDTO execute(UUID id, JobUpdateRequestDTO request) throws SystemGlobalException {
        return jobService.updateJob(id, request);
    }
}
