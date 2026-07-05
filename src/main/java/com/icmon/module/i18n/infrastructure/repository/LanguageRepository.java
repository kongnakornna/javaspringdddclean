package com.icmon.module.i18n.infrastructure.repository;

import com.icmon.module.i18n.infrastructure.entity.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity, UUID> {

    Optional<LanguageEntity> findByLanguageCode(String code);

    List<LanguageEntity> findByIsActiveTrue();

    Optional<LanguageEntity> findByIsDefaultTrue();

    List<LanguageEntity> findAllByOrderBySortOrderAsc();
}
