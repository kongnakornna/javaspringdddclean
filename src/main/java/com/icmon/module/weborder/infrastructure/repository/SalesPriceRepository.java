package com.icmon.module.weborder.infrastructure.repository;

import com.icmon.module.weborder.infrastructure.entity.SalesPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SalesPriceRepository extends JpaRepository<SalesPriceEntity, UUID> {
    List<SalesPriceEntity> findByItemIdAndIsActiveTrue(UUID itemId);

    List<SalesPriceEntity> findByItemIdAndPriceTierAndIsActiveTrue(UUID itemId, String priceTier);
}
