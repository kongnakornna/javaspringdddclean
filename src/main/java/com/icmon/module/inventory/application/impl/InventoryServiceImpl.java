package com.icmon.module.inventory.application.impl;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.InventoryService;
import com.icmon.module.inventory.domain.MPartMaster;
import com.icmon.module.inventory.domain.TInventory;
import com.icmon.module.inventory.domain.enums.TransactionType;
import com.icmon.module.inventory.infrastructure.cache.PartCacheService;
import com.icmon.module.inventory.infrastructure.entity.InventoryEntity;
import com.icmon.module.inventory.infrastructure.entity.InventoryLayerEntity;
import com.icmon.module.inventory.infrastructure.entity.PartMasterEntity;
import com.icmon.module.inventory.infrastructure.mapper.InventoryMapper;
import com.icmon.module.inventory.infrastructure.mapper.InventoryLayerMapper;
import com.icmon.module.inventory.infrastructure.mapper.PartMasterMapper;
import com.icmon.module.inventory.infrastructure.repository.InventoryLayerRepository;
import com.icmon.module.inventory.infrastructure.repository.InventoryRepository;
import com.icmon.module.inventory.infrastructure.repository.PartMasterRepository;
import com.icmon.module.inventory.presentation.dto.request.InventoryIssueRequestDTO;
import com.icmon.module.inventory.presentation.dto.request.InventoryReceiveRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.InventoryResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final PartMasterRepository partMasterRepository;
    private final InventoryRepository inventoryRepository;
    private final InventoryLayerRepository inventoryLayerRepository;
    private final PartCacheService partCacheService;
    private final PartMasterMapper partMasterMapper;
    private final InventoryMapper inventoryMapper;
    private final InventoryLayerMapper inventoryLayerMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InventoryResponseDTO receiveGoods(InventoryReceiveRequestDTO request) throws SystemGlobalException {
        PartMasterEntity entity = partMasterRepository.findById(request.getPartId())
                .orElseThrow(() -> new SystemGlobalException("Part not found: " + request.getPartId(), null));
        MPartMaster part = partMasterMapper.toDomain(entity);
        Integer previousQuantity = part.getStockQuantity() != null ? part.getStockQuantity() : 0;
        part.increaseStock(request.getQuantity());
        partMasterRepository.save(partMasterMapper.toEntity(part));
        partCacheService.savePart(part);
        InventoryLayerEntity layer = new InventoryLayerEntity();
        layer.setPartId(part.getId()); layer.setQuantity(request.getQuantity());
        layer.setUnitCost(request.getUnitCost()); layer.setReceivedDate(LocalDateTime.now());
        layer.setReferenceType(request.getReferenceType()); layer.setReferenceId(request.getReferenceId());
        layer.setIsActive(true); layer.setWhitelabelId(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        inventoryLayerRepository.save(layer);
        InventoryEntity tx = new InventoryEntity();
        tx.setPartId(part.getId()); tx.setTransactionType(TransactionType.RECEIVE.name());
        tx.setReferenceType(request.getReferenceType()); tx.setReferenceId(request.getReferenceId());
        tx.setQuantity(request.getQuantity()); tx.setPreviousQuantity(previousQuantity);
        tx.setNewQuantity(part.getStockQuantity()); tx.setUnitCost(request.getUnitCost());
        tx.setTotalCost(request.getUnitCost().multiply(new BigDecimal(request.getQuantity())));
        tx.setTransactionDate(LocalDateTime.now()); tx.setNote(request.getNote());
        tx.setPerformedBy(getCurrentUserId()); tx.setWhitelabelId(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        InventoryEntity saved = inventoryRepository.save(tx);
        log.info("Received - Part: {}, Qty: {}, New Stock: {}", part.getPartCode(), request.getQuantity(), part.getStockQuantity());
        return InventoryResponseDTO.fromEntity(saved, part);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InventoryResponseDTO issueGoods(InventoryIssueRequestDTO request) throws SystemGlobalException {
        PartMasterEntity entity = partMasterRepository.findById(request.getPartId())
                .orElseThrow(() -> new SystemGlobalException("Part not found: " + request.getPartId(), null));
        MPartMaster part = partMasterMapper.toDomain(entity);
        if (part.getStockQuantity() < request.getQuantity()) {
            throw new SystemGlobalException("Insufficient stock. Available: " + part.getStockQuantity(), null);
        }
        Integer previousQuantity = part.getStockQuantity();
        int remainingToIssue = request.getQuantity();
        BigDecimal totalCost = BigDecimal.ZERO;
        List<InventoryLayerEntity> layers = inventoryLayerRepository.findActiveLayersByPartIdOrderByDateAsc(part.getId());
        for (InventoryLayerEntity layerEntity : layers) {
            if (remainingToIssue <= 0) break;
            int qtyFromLayer = Math.min(remainingToIssue, layerEntity.getQuantity());
            totalCost = totalCost.add(layerEntity.getUnitCost().multiply(new BigDecimal(qtyFromLayer)));
            layerEntity.setQuantity(layerEntity.getQuantity() - qtyFromLayer);
            if (layerEntity.getQuantity() == 0) layerEntity.setIsActive(false);
            inventoryLayerRepository.save(layerEntity);
            remainingToIssue -= qtyFromLayer;
        }
        part.decreaseStock(request.getQuantity());
        partMasterRepository.save(partMasterMapper.toEntity(part));
        partCacheService.savePart(part);
        BigDecimal avgCost = totalCost.divide(new BigDecimal(request.getQuantity()), 2, RoundingMode.HALF_UP);
        InventoryEntity tx = new InventoryEntity();
        tx.setPartId(part.getId()); tx.setTransactionType(TransactionType.ISSUE.name());
        tx.setReferenceType(request.getReferenceType()); tx.setReferenceId(request.getReferenceId());
        tx.setQuantity(-request.getQuantity()); tx.setPreviousQuantity(previousQuantity);
        tx.setNewQuantity(part.getStockQuantity()); tx.setUnitCost(avgCost);
        tx.setTotalCost(totalCost); tx.setTransactionDate(LocalDateTime.now());
        tx.setNote(request.getNote()); tx.setPerformedBy(getCurrentUserId());
        tx.setWhitelabelId(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        InventoryEntity saved = inventoryRepository.save(tx);
        log.info("Issued - Part: {}, Qty: {}, Avg Cost: {}", part.getPartCode(), request.getQuantity(), avgCost);
        return InventoryResponseDTO.fromEntity(saved, part);
    }

    @Override
    public List<InventoryResponseDTO> getInventoryByPartId(UUID partId) throws SystemGlobalException {
        return inventoryRepository.findByPartIdOrderByTransactionDateDesc(partId).stream()
                .map(e -> InventoryResponseDTO.fromEntity(e, null))
                .collect(Collectors.toList());
    }

    private UUID getCurrentUserId() {
        return UUID.fromString("00000000-0000-0000-0000-000000000001");
    }
}
