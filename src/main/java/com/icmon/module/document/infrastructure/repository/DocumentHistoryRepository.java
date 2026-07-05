package com.icmon.module.document.infrastructure.repository;

import com.icmon.module.document.infrastructure.entity.DocumentHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DocumentHistoryRepository extends JpaRepository<DocumentHistoryEntity, UUID> {
    List<DocumentHistoryEntity> findByDocumentIdOrderByPerformedAtDesc(UUID documentId);
}
