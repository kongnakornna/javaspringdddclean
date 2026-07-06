package com.icmon.module.weborder.infrastructure.repository;

import com.icmon.module.weborder.infrastructure.entity.WebOrderStatusHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderStatusHistoryRepository extends JpaRepository<WebOrderStatusHistoryEntity, UUID> {
    List<WebOrderStatusHistoryEntity> findByOrderIdOrderByChangedAtAsc(UUID orderId);
}
