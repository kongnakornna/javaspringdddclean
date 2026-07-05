package com.icmon.module.inventory.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.inventory.application.interfaces.PartMasterService;
import com.icmon.module.inventory.infrastructure.cache.PartMasterCacheService;
import com.icmon.module.inventory.infrastructure.entity.PartMasterEntity;
import com.icmon.module.inventory.infrastructure.mapper.PartMasterMapper;
import com.icmon.module.inventory.infrastructure.repository.PartMasterRepository;
import com.icmon.module.inventory.presentation.dto.request.PartMasterCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.request.PartMasterUpdateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.PartMasterResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PartMasterServiceImpl extends GenericAuthDomainServiceImpl implements PartMasterService {

    private final PartMasterRepository repository;
    private final PartMasterMapper mapper;
    private final PartMasterCacheService cacheService;

    @Override
    @Transactional
    public PartMasterResponseDTO createPart(PartMasterCreateRequestDTO request) throws SystemGlobalException {
        if (repository.findByPartCode(request.getPartCode()).isPresent()) {
            throw new FailedRequestException("Part code already exists: " + request.getPartCode(), null);
        }
        PartMasterEntity entity = new PartMasterEntity();
        entity.setPartCode(request.getPartCode());
        entity.setPartName(request.getPartName());
        entity.setPartNameEn(request.getPartNameEn());
        entity.setCategoryId(request.getCategoryId());
        entity.setBrand(request.getBrand());
        entity.setModel(request.getModel());
        entity.setOemNumber(request.getOemNumber());
        entity.setDescription(request.getDescription());
        entity.setUnit(request.getUnit() != null ? request.getUnit() : "PIECE");
        entity.setReorderLevel(request.getReorderLevel());
        entity.setReorderQuantity(request.getReorderQuantity());
        entity.setMinStock(request.getMinStock());
        entity.setMaxStock(request.getMaxStock());
        entity.setUnitCost(request.getUnitCost());
        entity.setSellingPrice(request.getSellingPrice());
        entity.setLocationId(request.getLocationId());
        entity.setImageUrl(request.getImageUrl());
        entity.setNotes(request.getNotes());
        entity.setStatus("ACTIVE");
        PartMasterEntity saved = repository.save(entity);
        cacheService.cache(saved);
        return toResponseDTO(saved);
    }

    @Override
    public PartMasterResponseDTO getPart(UUID id) throws SystemGlobalException {
        PartMasterEntity entity = repository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Part not found with id: " + id, null));
        return toResponseDTO(entity);
    }

    @Override
    public PartMasterResponseDTO getPartByCode(String partCode) throws SystemGlobalException {
        PartMasterEntity entity = repository.findByPartCode(partCode)
                .orElseThrow(() -> new FailedRequestException("Part not found with code: " + partCode, null));
        return toResponseDTO(entity);
    }

    @Override
    public Page<PartMasterResponseDTO> searchParts(String search, UUID categoryId, String status, Pageable pageable) throws SystemGlobalException {
        return repository.searchParts(search, categoryId, status, pageable)
                .map(this::toResponseDTO);
    }

    @Override
    @Transactional
    public PartMasterResponseDTO updatePart(UUID id, PartMasterUpdateRequestDTO request) throws SystemGlobalException {
        PartMasterEntity entity = repository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Part not found with id: " + id, null));
        if (request.getPartName() != null) entity.setPartName(request.getPartName());
        if (request.getPartNameEn() != null) entity.setPartNameEn(request.getPartNameEn());
        if (request.getCategoryId() != null) entity.setCategoryId(request.getCategoryId());
        if (request.getBrand() != null) entity.setBrand(request.getBrand());
        if (request.getModel() != null) entity.setModel(request.getModel());
        if (request.getOemNumber() != null) entity.setOemNumber(request.getOemNumber());
        if (request.getDescription() != null) entity.setDescription(request.getDescription());
        if (request.getUnit() != null) entity.setUnit(request.getUnit());
        if (request.getReorderLevel() != 0) entity.setReorderLevel(request.getReorderLevel());
        if (request.getReorderQuantity() != 0) entity.setReorderQuantity(request.getReorderQuantity());
        if (request.getMinStock() != 0) entity.setMinStock(request.getMinStock());
        if (request.getMaxStock() != 0) entity.setMaxStock(request.getMaxStock());
        if (request.getUnitCost() != null) entity.setUnitCost(request.getUnitCost());
        if (request.getSellingPrice() != null) entity.setSellingPrice(request.getSellingPrice());
        if (request.getLocationId() != null) entity.setLocationId(request.getLocationId());
        if (request.getStatus() != null) entity.setStatus(request.getStatus());
        if (request.getImageUrl() != null) entity.setImageUrl(request.getImageUrl());
        if (request.getNotes() != null) entity.setNotes(request.getNotes());
        PartMasterEntity saved = repository.save(entity);
        cacheService.evict(id);
        cacheService.cache(saved);
        return toResponseDTO(saved);
    }

    @Override
    @Transactional
    public void deletePart(UUID id) throws SystemGlobalException {
        PartMasterEntity entity = repository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Part not found with id: " + id, null));
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        repository.save(entity);
        cacheService.evict(id);
    }

    @Override
    @Transactional
    public PartMasterResponseDTO updateStockQuantity(UUID id, int quantity) throws SystemGlobalException {
        PartMasterEntity entity = repository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Part not found with id: " + id, null));
        entity.setStockQuantity(quantity);
        entity.setLastUpdatedStock(LocalDateTime.now());
        PartMasterEntity saved = repository.save(entity);
        cacheService.evict(id);
        cacheService.cache(saved);
        return toResponseDTO(saved);
    }

    private PartMasterResponseDTO toResponseDTO(PartMasterEntity entity) {
        PartMasterResponseDTO dto = new PartMasterResponseDTO();
        dto.setId(entity.getId());
        dto.setPartCode(entity.getPartCode());
        dto.setPartName(entity.getPartName());
        dto.setPartNameEn(entity.getPartNameEn());
        dto.setCategoryId(entity.getCategoryId());
        dto.setBrand(entity.getBrand());
        dto.setModel(entity.getModel());
        dto.setOemNumber(entity.getOemNumber());
        dto.setDescription(entity.getDescription());
        dto.setUnit(entity.getUnit());
        dto.setReorderLevel(entity.getReorderLevel());
        dto.setReorderQuantity(entity.getReorderQuantity());
        dto.setStockQuantity(entity.getStockQuantity());
        dto.setMinStock(entity.getMinStock());
        dto.setMaxStock(entity.getMaxStock());
        dto.setUnitCost(entity.getUnitCost());
        dto.setSellingPrice(entity.getSellingPrice());
        dto.setLocationId(entity.getLocationId());
        dto.setStatus(entity.getStatus());
        dto.setImageUrl(entity.getImageUrl());
        dto.setNotes(entity.getNotes());
        dto.setLastUpdatedStock(entity.getLastUpdatedStock());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
