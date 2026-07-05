package com.icmon.module.document.infrastructure.repository;

import com.icmon.module.document.infrastructure.entity.DocumentTemplateEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentTemplateRepository extends JpaRepository<DocumentTemplateEntity, UUID> {
    Optional<DocumentTemplateEntity> findByTemplateCode(String templateCode);
    Page<DocumentTemplateEntity> findByTemplateType(String templateType, Pageable pageable);
    Page<DocumentTemplateEntity> findByIsActiveTrue(Pageable pageable);
}
