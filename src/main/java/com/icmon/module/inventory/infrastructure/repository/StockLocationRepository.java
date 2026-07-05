package com.icmon.module.inventory.infrastructure.repository;

import com.icmon.module.inventory.infrastructure.entity.StockLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StockLocationRepository extends JpaRepository<StockLocationEntity, UUID> {
    Optional<StockLocationEntity> findByLocationCode(String locationCode);
    List<StockLocationEntity> findByZone(String zone);
    List<StockLocationEntity> findByIsActiveTrue();
}
