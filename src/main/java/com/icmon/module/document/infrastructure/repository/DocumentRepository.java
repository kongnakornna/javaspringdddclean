package com.icmon.module.document.infrastructure.repository;

import com.icmon.module.document.infrastructure.entity.DocumentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, UUID> {
    Optional<DocumentEntity> findByDocumentNo(String documentNo);

    @Query("SELECT d FROM DocumentEntity d WHERE d.referenceType = :refType AND d.referenceId = :refId ORDER BY d.generatedAt DESC")
    List<DocumentEntity> findByReference(@Param("refType") String refType, @Param("refId") UUID refId);

    @Query("SELECT d FROM DocumentEntity d WHERE d.deleted = false AND " +
           "(:type IS NULL OR d.documentType = :type) AND " +
           "(:status IS NULL OR d.status = :status) AND " +
           "(:subType IS NULL OR d.documentSubType = :subType)")
    Page<DocumentEntity> searchDocuments(@Param("type") String type,
                                         @Param("status") String status,
                                         @Param("subType") String subType,
                                         Pageable pageable);

    List<DocumentEntity> findByTemplateId(UUID templateId);
}
