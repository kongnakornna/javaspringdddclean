package com.icmon.module.inventory.infrastructure.repository;

import com.icmon.module.inventory.infrastructure.entity.PartPickingDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface PartPickingDetailRepository extends JpaRepository<PartPickingDetailEntity, UUID> {
    List<PartPickingDetailEntity> findByPickingRequestId(UUID pickingRequestId);
}
