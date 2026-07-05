package com.icmon.module.email.infrastructure.repository;

import com.icmon.module.email.infrastructure.entity.EmailHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailHistoryRepository extends JpaRepository<EmailHistoryEntity, UUID> {

    Optional<EmailHistoryEntity> findByEmailId(String emailId);

    Page<EmailHistoryEntity> findByStatus(String status, Pageable pageable);

    Page<EmailHistoryEntity> findByTemplateCode(String templateCode, Pageable pageable);

    Page<EmailHistoryEntity> findByReferenceTypeAndReferenceId(String referenceType, UUID referenceId, Pageable pageable);

    @Query("SELECT h FROM EmailHistoryEntity h WHERE " +
           "(:status IS NULL OR h.status = :status) AND " +
           "(:templateCode IS NULL OR h.templateCode = :templateCode) AND " +
           "(:toEmail IS NULL OR h.toEmail = :toEmail) " +
           "ORDER BY h.createdAt DESC")
    Page<EmailHistoryEntity> searchHistories(
            @Param("status") String status,
            @Param("templateCode") String templateCode,
            @Param("toEmail") String toEmail,
            Pageable pageable);

    long countByStatus(String status);
}
