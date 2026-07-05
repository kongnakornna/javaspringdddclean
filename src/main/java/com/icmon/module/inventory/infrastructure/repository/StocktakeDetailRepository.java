package com.icmon.module.inventory.infrastructure.repository;

import com.icmon.module.inventory.infrastructure.entity.StocktakeDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StocktakeDetailRepository extends JpaRepository<StocktakeDetailEntity, UUID> {
    List<StocktakeDetailEntity> findByStocktakeHeaderId(UUID stocktakeHeaderId);
    List<StocktakeDetailEntity> findByPartId(UUID partId);
}
