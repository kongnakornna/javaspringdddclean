package com.icmon.module.email.infrastructure.repository;

import com.icmon.module.email.infrastructure.entity.EmailTemplateEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplateEntity, UUID> {

    Optional<EmailTemplateEntity> findByTemplateCodeAndLanguage(String templateCode, String language);

    Optional<EmailTemplateEntity> findByTemplateCodeAndLanguageAndIsActiveTrue(String templateCode, String language);

    Page<EmailTemplateEntity> findByCategory(String category, Pageable pageable);

    Page<EmailTemplateEntity> findByLanguage(String language, Pageable pageable);

    Page<EmailTemplateEntity> findByCategoryAndLanguage(String category, String language, Pageable pageable);

    boolean existsByTemplateCode(String templateCode);
}
