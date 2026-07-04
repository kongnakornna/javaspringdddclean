package com.icmon.module.job.infrastructure.repository;

import com.icmon.module.job.infrastructure.entity.JobPartSalesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobPartSalesRepository extends JpaRepository<JobPartSalesEntity, UUID> {
    List<JobPartSalesEntity> findByJobId(UUID jobId);
    void deleteByJobId(UUID jobId);
}
