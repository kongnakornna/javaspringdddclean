package com.icmon.module.weborder.infrastructure.repository;

import com.icmon.module.weborder.infrastructure.entity.CatalogueCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CatalogueCategoryRepository extends JpaRepository<CatalogueCategoryEntity, UUID> {
    Optional<CatalogueCategoryEntity> findByCategoryCode(String categoryCode);

    List<CatalogueCategoryEntity> findByIsActiveTrueAndDeletedFalseOrderBySortOrder();

    List<CatalogueCategoryEntity> findByParentIdAndDeletedFalse(UUID parentId);
}
