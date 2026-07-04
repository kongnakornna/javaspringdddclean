package com.icmon.module.job.infrastructure.repository;

import com.icmon.module.job.infrastructure.entity.JobServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobServiceRepository extends JpaRepository<JobServiceEntity, UUID> {
    List<JobServiceEntity> findByJobId(UUID jobId);
    void deleteByJobId(UUID jobId);
}
