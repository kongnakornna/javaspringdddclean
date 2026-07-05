package com.icmon.module.customer.infrastructure.repository;

import com.icmon.module.customer.infrastructure.entity.CarServiceHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarServiceHistoryRepository extends JpaRepository<CarServiceHistoryEntity, UUID> {
    List<CarServiceHistoryEntity> findByCarIdOrderByServiceDateDesc(UUID carId);
}
