package com.icmon.module.email.infrastructure.repository;

import com.icmon.module.email.infrastructure.entity.EmailQueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailQueueRepository extends JpaRepository<EmailQueueEntity, UUID> {

    Optional<EmailQueueEntity> findByEmailId(String emailId);

    List<EmailQueueEntity> findByStatusOrderByPriorityDescCreatedAtAsc(String status);

    @Query("SELECT q FROM EmailQueueEntity q WHERE q.status = 'PENDING' AND q.nextAttemptAt <= :now ORDER BY " +
           "CASE q.priority WHEN 'URGENT' THEN 0 WHEN 'HIGH' THEN 1 WHEN 'NORMAL' THEN 2 ELSE 3 END, q.createdAt ASC")
    List<EmailQueueEntity> findPendingQueue(@Param("now") LocalDateTime now);

    @Query("SELECT q FROM EmailQueueEntity q WHERE q.status = 'FAILED' AND q.retryCount < q.maxRetry AND q.nextAttemptAt <= :now")
    List<EmailQueueEntity> findRetryableQueue(@Param("now") LocalDateTime now);

    long countByStatus(String status);

    void deleteByEmailId(String emailId);
}
