package com.icmon.module.weborder.infrastructure.repository;

import com.icmon.module.weborder.infrastructure.entity.PromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PromotionRepository extends JpaRepository<PromotionEntity, UUID> {
    Optional<PromotionEntity> findByPromotionCode(String promotionCode);
}
