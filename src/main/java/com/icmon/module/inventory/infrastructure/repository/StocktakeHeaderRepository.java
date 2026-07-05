package com.icmon.module.inventory.infrastructure.repository;

import com.icmon.module.inventory.infrastructure.entity.StocktakeHeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StocktakeHeaderRepository extends JpaRepository<StocktakeHeaderEntity, UUID> {
    Optional<StocktakeHeaderEntity> findByStocktakeNo(String stocktakeNo);
    List<StocktakeHeaderEntity> findByStatus(String status);
}
