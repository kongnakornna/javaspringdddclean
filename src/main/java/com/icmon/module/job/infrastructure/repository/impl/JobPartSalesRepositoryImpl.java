package com.icmon.module.job.infrastructure.repository.impl;

import com.icmon.module.job.infrastructure.entity.JobPartSalesEntity;
import com.icmon.module.job.infrastructure.repository.JobPartSalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JobPartSalesRepositoryImpl {
    private final JobPartSalesRepository jobPartSalesRepository;

    public JobPartSalesEntity save(JobPartSalesEntity entity) {
        return jobPartSalesRepository.save(entity);
    }

    public List<JobPartSalesEntity> findByJobId(UUID jobId) {
        return jobPartSalesRepository.findByJobId(jobId);
    }

    public void deleteByJobId(UUID jobId) {
        jobPartSalesRepository.deleteByJobId(jobId);
    }
}
