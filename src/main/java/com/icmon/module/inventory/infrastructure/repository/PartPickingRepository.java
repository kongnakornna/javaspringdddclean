package com.icmon.module.inventory.infrastructure.repository;

import com.icmon.module.inventory.infrastructure.entity.PartPickingRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface PartPickingRepository extends JpaRepository<PartPickingRequestEntity, UUID> {
    List<PartPickingRequestEntity> findByJobId(UUID jobId);
}
