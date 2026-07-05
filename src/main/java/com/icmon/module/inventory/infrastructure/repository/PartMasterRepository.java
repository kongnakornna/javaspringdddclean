package com.icmon.module.inventory.infrastructure.repository;

import com.icmon.module.inventory.infrastructure.entity.PartMasterEntity;
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
public interface PartMasterRepository extends JpaRepository<PartMasterEntity, UUID> {
    Optional<PartMasterEntity> findByPartCode(String partCode);
    List<PartMasterEntity> findByCategoryId(UUID categoryId);
    List<PartMasterEntity> findByStatus(String status);
    List<PartMasterEntity> findByBrand(String brand);
    List<PartMasterEntity> findByOemNumber(String oemNumber);
    List<PartMasterEntity> findByLocationId(UUID locationId);

    @Query("SELECT p FROM PartMasterEntity p WHERE p.deleted = false " +
           "AND (:search IS NULL OR LOWER(p.partName) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(p.partCode) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "AND (:categoryId IS NULL OR p.categoryId = :categoryId) " +
           "AND (:status IS NULL OR p.status = :status)")
    Page<PartMasterEntity> searchParts(
            @Param("search") String search,
            @Param("categoryId") UUID categoryId,
            @Param("status") String status,
            Pageable pageable);

    List<PartMasterEntity> findByStockQuantityLessThanEqual(int reorderLevel);
}
