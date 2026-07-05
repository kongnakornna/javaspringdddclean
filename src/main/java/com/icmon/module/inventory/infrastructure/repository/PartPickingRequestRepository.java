package com.icmon.module.inventory.infrastructure.repository;

import com.icmon.module.inventory.infrastructure.entity.PartPickingRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PartPickingRequestRepository extends JpaRepository<PartPickingRequestEntity, UUID> {
    Optional<PartPickingRequestEntity> findByPickingNo(String pickingNo);
    List<PartPickingRequestEntity> findByStatus(String status);
    List<PartPickingRequestEntity> findByJobId(UUID jobId);
    List<PartPickingRequestEntity> findByQuotationId(UUID quotationId);
    List<PartPickingRequestEntity> findByRequestedBy(UUID requestedBy);
}
