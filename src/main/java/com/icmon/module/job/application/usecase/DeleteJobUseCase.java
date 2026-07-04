package com.icmon.module.job.application.usecase;

import com.icmon.module.job.application.interfaces.JobService;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteJobUseCase {
    private final JobService jobService;

    public void execute(UUID id) throws SystemGlobalException {
        jobService.deleteJob(id);
    }
}
