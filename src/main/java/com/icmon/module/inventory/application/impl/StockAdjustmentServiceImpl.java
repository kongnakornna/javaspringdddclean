package com.icmon.module.inventory.application.impl;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.StockAdjustmentService;
import com.icmon.module.inventory.infrastructure.entity.StockAdjustmentHeaderEntity;
import com.icmon.module.inventory.infrastructure.repository.PartMasterRepository;
import com.icmon.module.inventory.infrastructure.repository.StockAdjustmentRepository;
import com.icmon.module.inventory.presentation.dto.request.AdjustmentRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.AdjustmentResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockAdjustmentServiceImpl implements StockAdjustmentService {
    private final StockAdjustmentRepository adjustmentRepository;
    private final PartMasterRepository partMasterRepository;

    @Override
    @Transactional
    public AdjustmentResponseDTO createAdjustment(AdjustmentRequestDTO request) throws SystemGlobalException {
        partMasterRepository.findById(request.getPartId())
                .orElseThrow(() -> new SystemGlobalException("Part not found: " + request.getPartId(), null));
        StockAdjustmentHeaderEntity entity = new StockAdjustmentHeaderEntity();
        entity.setAdjustmentDate(LocalDateTime.now());
        entity.setAdjustmentType(request.getQuantity() >= 0 ? "INCREASE" : "DECREASE");
        entity.setReason(request.getReason());
        entity.setStatus("DRAFT");
        entity.setDescription(request.getDescription());
        entity.setTotalAdjustmentValue(request.getUnitCost() != null
                ? request.getUnitCost().multiply(new BigDecimal(Math.abs(request.getQuantity()))) : BigDecimal.ZERO);
        entity.setWhitelabelId(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        StockAdjustmentHeaderEntity saved = adjustmentRepository.save(entity);
        log.info("Created adjustment: {}", saved.getAdjustmentNo());
        return AdjustmentResponseDTO.fromEntity(saved);
    }

    @Override
    @Transactional
    public AdjustmentResponseDTO approveAdjustment(UUID id) throws SystemGlobalException {
        StockAdjustmentHeaderEntity entity = adjustmentRepository.findById(id)
                .orElseThrow(() -> new SystemGlobalException("Adjustment not found: " + id, null));
        entity.setStatus("APPROVED");
        entity.setApprovedBy(getCurrentUserId());
        entity.setApprovedAt(LocalDateTime.now());
        StockAdjustmentHeaderEntity saved = adjustmentRepository.save(entity);
        log.info("Approved adjustment: {}", saved.getAdjustmentNo());
        return AdjustmentResponseDTO.fromEntity(saved);
    }

    private UUID getCurrentUserId() {
        return UUID.fromString("00000000-0000-0000-0000-000000000001");
    }
}
