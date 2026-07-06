package com.icmon.module.inventory.infrastructure.repository;

import com.icmon.module.inventory.infrastructure.entity.InventoryAlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface InventoryAlertRepository extends JpaRepository<InventoryAlertEntity, UUID> {
    boolean existsByAlertDateAndPartId(LocalDate alertDate, UUID partId);
}
