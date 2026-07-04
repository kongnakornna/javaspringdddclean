package com.icmon.module.job.application.usecase;

import com.icmon.module.job.application.interfaces.JobService;
import com.icmon.module.job.presentation.dto.response.JobResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetJobUseCase {
    private final JobService jobService;

    public JobResponseDTO execute(UUID id) throws SystemGlobalException {
        return jobService.getJob(id);
    }
}
