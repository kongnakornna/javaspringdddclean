package com.icmon.module.job.infrastructure.repository.impl;

import com.icmon.module.job.domain.enums.JobStatus;
import com.icmon.module.job.infrastructure.entity.JobEntity;
import com.icmon.module.job.infrastructure.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JobRepositoryImpl {
    private final JobRepository jobRepository;

    public JobEntity save(JobEntity entity) {
        return jobRepository.save(entity);
    }

    public Optional<JobEntity> findById(UUID id) {
        return jobRepository.findById(id);
    }

    public Optional<JobEntity> findByJobNo(String jobNo) {
        return jobRepository.findByJobNo(jobNo);
    }

    public Page<JobEntity> findByStatus(JobStatus status, Pageable pageable) {
        return jobRepository.findByStatus(status, pageable);
    }

    public void deleteById(UUID id) {
        jobRepository.deleteById(id);
    }
}
