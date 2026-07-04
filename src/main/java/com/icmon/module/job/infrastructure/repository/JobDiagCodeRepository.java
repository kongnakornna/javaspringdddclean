package com.icmon.module.job.infrastructure.repository;

import com.icmon.module.job.infrastructure.entity.JobDiagCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobDiagCodeRepository extends JpaRepository<JobDiagCodeEntity, UUID> {
    List<JobDiagCodeEntity> findByJobId(UUID jobId);
}
