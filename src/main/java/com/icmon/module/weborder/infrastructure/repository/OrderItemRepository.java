package com.icmon.module.weborder.infrastructure.repository;

import com.icmon.module.weborder.infrastructure.entity.WebOrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<WebOrderItemEntity, UUID> {
    List<WebOrderItemEntity> findByOrderId(UUID orderId);
}
