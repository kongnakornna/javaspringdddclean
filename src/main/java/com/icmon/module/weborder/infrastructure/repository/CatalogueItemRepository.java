package com.icmon.module.weborder.infrastructure.repository;

import com.icmon.module.weborder.infrastructure.entity.CatalogueItemEntity;
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
public interface CatalogueItemRepository extends JpaRepository<CatalogueItemEntity, UUID> {
    Optional<CatalogueItemEntity> findByItemCode(String itemCode);

    Page<CatalogueItemEntity> findByCategoryIdAndDeletedFalse(UUID categoryId, Pageable pageable);

    List<CatalogueItemEntity> findByIsFeaturedTrueAndIsActiveTrueAndDeletedFalse();

    @Query("SELECT c FROM CatalogueItemEntity c WHERE c.deleted = false " +
           "AND (:keyword IS NULL OR LOWER(c.itemName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(c.itemCode) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(c.brand) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<CatalogueItemEntity> searchItems(@Param("keyword") String keyword, Pageable pageable);

    Page<CatalogueItemEntity> findByDeletedFalse(Pageable pageable);
}
