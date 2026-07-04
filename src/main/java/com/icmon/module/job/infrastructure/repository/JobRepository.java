package com.icmon.module.job.infrastructure.repository;

import com.icmon.module.job.domain.enums.JobStatus;
import com.icmon.module.job.infrastructure.entity.JobEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, UUID> {
    Optional<JobEntity> findByJobNo(String jobNo);
    Page<JobEntity> findByStatus(JobStatus status, Pageable pageable);
    Page<JobEntity> findByStatusAndStartDateBetween(JobStatus status, LocalDateTime from, LocalDateTime to, Pageable pageable);
    Page<JobEntity> findByCustomerId(UUID customerId, Pageable pageable);
    Page<JobEntity> findByMechanicId(UUID mechanicId, Pageable pageable);
}
