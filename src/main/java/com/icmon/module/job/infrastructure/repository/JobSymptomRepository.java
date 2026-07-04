package com.icmon.module.job.infrastructure.repository;

import com.icmon.module.job.infrastructure.entity.JobSymptomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobSymptomRepository extends JpaRepository<JobSymptomEntity, UUID> {
    List<JobSymptomEntity> findByJobId(UUID jobId);
}
