package com.icmon.module.job.infrastructure.repository;

import com.icmon.module.job.infrastructure.entity.JobStatusHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobStatusHistoryRepository extends JpaRepository<JobStatusHistoryEntity, UUID> {
    List<JobStatusHistoryEntity> findByJobIdOrderByChangedAtAsc(UUID jobId);
}
