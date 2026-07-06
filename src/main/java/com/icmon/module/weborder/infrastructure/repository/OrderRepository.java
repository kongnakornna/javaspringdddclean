package com.icmon.module.weborder.infrastructure.repository;

import com.icmon.module.weborder.infrastructure.entity.WebOrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<WebOrderEntity, UUID> {
    Optional<WebOrderEntity> findByOrderNo(String orderNo);

    @Query("SELECT o FROM WebOrderEntity o WHERE o.deleted = false " +
           "AND (:status IS NULL OR o.status = :status) " +
           "AND (:startDate IS NULL OR o.orderDate >= :startDate) " +
           "AND (:endDate IS NULL OR o.orderDate <= :endDate)")
    Page<WebOrderEntity> searchOrders(
            @Param("status") String status,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            Pageable pageable);
}
