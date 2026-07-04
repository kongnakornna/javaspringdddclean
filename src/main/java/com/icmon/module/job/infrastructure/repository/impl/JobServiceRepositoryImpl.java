package com.icmon.module.job.infrastructure.repository.impl;

import com.icmon.module.job.infrastructure.entity.JobServiceEntity;
import com.icmon.module.job.infrastructure.repository.JobServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JobServiceRepositoryImpl {
    private final JobServiceRepository jobServiceRepository;

    public JobServiceEntity save(JobServiceEntity entity) {
        return jobServiceRepository.save(entity);
    }

    public List<JobServiceEntity> findByJobId(UUID jobId) {
        return jobServiceRepository.findByJobId(jobId);
    }

    public void deleteByJobId(UUID jobId) {
        jobServiceRepository.deleteByJobId(jobId);
    }
}
