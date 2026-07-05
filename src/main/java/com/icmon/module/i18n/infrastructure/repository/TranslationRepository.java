package com.icmon.module.i18n.infrastructure.repository;

import com.icmon.module.i18n.infrastructure.entity.TranslationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TranslationRepository extends JpaRepository<TranslationEntity, UUID> {

    Optional<TranslationEntity> findByMessageKeyAndLanguageCode(String messageKey, String languageCode);

    List<TranslationEntity> findByLanguageCode(String languageCode);

    List<TranslationEntity> findByMessageKey(String messageKey);

    Page<TranslationEntity> findByLanguageCodeAndContext(String languageCode, String context, Pageable pageable);

    Page<TranslationEntity> findByMessageKeyContaining(String messageKey, Pageable pageable);
}
