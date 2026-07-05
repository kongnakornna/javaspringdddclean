package com.icmon.module.inventory.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.inventory.application.interfaces.InventoryService;
import com.icmon.module.inventory.infrastructure.cache.InventoryCacheService;
import com.icmon.module.inventory.infrastructure.cache.PartMasterCacheService;
import com.icmon.module.inventory.infrastructure.entity.InventoryEntity;
import com.icmon.module.inventory.infrastructure.entity.PartMasterEntity;
import com.icmon.module.inventory.infrastructure.repository.InventoryRepository;
import com.icmon.module.inventory.infrastructure.repository.PartMasterRepository;
import com.icmon.module.inventory.presentation.dto.request.InventoryTransactionRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.InventoryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl extends GenericAuthDomainServiceImpl implements InventoryService {

    private final InventoryRepository repository;
    private final PartMasterRepository partMasterRepository;
    private final InventoryCacheService cacheService;
    private final PartMasterCacheService partCacheService;

    @Override
    @Transactional
    public InventoryResponseDTO recordTransaction(InventoryTransactionRequestDTO request) throws SystemGlobalException {
        PartMasterEntity part = partMasterRepository.findById(request.getPartId())
                .orElseThrow(() -> new FailedRequestException("Part not found with id: " + request.getPartId(), null));

        int previousQty = part.getStockQuantity();
        int newQty;
        switch (request.getTransactionType().toUpperCase()) {
            case "RECEIPT":
            case "RETURN":
            case "TRANSFER_IN":
            case "ADJUSTMENT":
                newQty = previousQty + request.getQuantity();
                break;
            case "ISSUE":
            case "TRANSFER_OUT":
                newQty = previousQty - request.getQuantity();
                break;
            default:
                newQty = previousQty;
        }
        if (newQty < 0) {
            throw new FailedRequestException("Insufficient stock quantity", null);
        }

        part.setStockQuantity(newQty);
        part.setLastUpdatedStock(LocalDateTime.now());
        partMasterRepository.save(part);
        partCacheService.evict(request.getPartId());

        InventoryEntity entity = new InventoryEntity();
        entity.setPartId(request.getPartId());
        entity.setTransactionType(request.getTransactionType());
        entity.setReferenceType(request.getReferenceType());
        entity.setReferenceId(request.getReferenceId());
        entity.setQuantity(request.getQuantity());
        entity.setPreviousQuantity(previousQty);
        entity.setNewQuantity(newQty);
        entity.setUnitCost(request.getUnitCost());
        entity.setTotalCost(request.getUnitCost() != null ? request.getUnitCost().multiply(java.math.BigDecimal.valueOf(request.getQuantity())) : null);
        entity.setTransactionDate(LocalDateTime.now());
        entity.setNote(request.getNote());
        entity.setPerformedBy(request.getPerformedBy());
        InventoryEntity saved = repository.save(entity);
        cacheService.cache(saved);
        return toResponseDTO(saved);
    }

    @Override
    public InventoryResponseDTO getTransaction(UUID id) throws SystemGlobalException {
        InventoryEntity entity = repository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Transaction not found with id: " + id, null));
        return toResponseDTO(entity);
    }

    @Override
    public Page<InventoryResponseDTO> searchTransactions(UUID partId, String transactionType,
                                                          LocalDateTime startDate, LocalDateTime endDate,
                                                          Pageable pageable) throws SystemGlobalException {
        return repository.searchTransactions(partId, transactionType, startDate, endDate, pageable)
                .map(this::toResponseDTO);
    }

    private InventoryResponseDTO toResponseDTO(InventoryEntity entity) {
        InventoryResponseDTO dto = new InventoryResponseDTO();
        dto.setId(entity.getId());
        dto.setPartId(entity.getPartId());
        dto.setTransactionType(entity.getTransactionType());
        dto.setReferenceType(entity.getReferenceType());
        dto.setReferenceId(entity.getReferenceId());
        dto.setQuantity(entity.getQuantity());
        dto.setPreviousQuantity(entity.getPreviousQuantity());
        dto.setNewQuantity(entity.getNewQuantity());
        dto.setUnitCost(entity.getUnitCost());
        dto.setTotalCost(entity.getTotalCost());
        dto.setTransactionDate(entity.getTransactionDate());
        dto.setNote(entity.getNote());
        dto.setPerformedBy(entity.getPerformedBy());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
