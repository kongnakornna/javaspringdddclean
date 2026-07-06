package com.icmon.module.inventory.application.impl;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.PartMasterService;
import com.icmon.module.inventory.domain.MPartMaster;
import com.icmon.module.inventory.infrastructure.cache.PartCacheService;
import com.icmon.module.inventory.infrastructure.entity.PartMasterEntity;
import com.icmon.module.inventory.infrastructure.mapper.PartMasterMapper;
import com.icmon.module.inventory.infrastructure.repository.PartMasterRepository;
import com.icmon.module.inventory.presentation.dto.request.PartCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.request.PartUpdateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.PartResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartMasterServiceImpl implements PartMasterService {
    private final PartMasterRepository partMasterRepository;
    private final PartMasterMapper partMasterMapper;
    private final PartCacheService partCacheService;

    @Override
    @Transactional
    public PartResponseDTO createPart(PartCreateRequestDTO request) throws SystemGlobalException {
        MPartMaster part = new MPartMaster();
        part.setPartCode(request.getPartCode());
        part.setPartName(request.getPartName());
        part.setPartNameEn(request.getPartNameEn());
        part.setCategoryId(request.getCategoryId());
        part.setBrand(request.getBrand());
        part.setModel(request.getModel());
        part.setOemNumber(request.getOemNumber());
        part.setDescription(request.getDescription());
        part.setUnit(request.getUnit() != null ? request.getUnit() : "PIECE");
        part.setReorderLevel(request.getReorderLevel() != null ? request.getReorderLevel() : 0);
        part.setReorderQuantity(request.getReorderQuantity() != null ? request.getReorderQuantity() : 0);
        part.setStockQuantity(0);
        part.setMinStock(request.getMinStock() != null ? request.getMinStock() : 0);
        part.setMaxStock(request.getMaxStock() != null ? request.getMaxStock() : 0);
        part.setUnitCost(request.getUnitCost());
        part.setSellingPrice(request.getSellingPrice());
        part.setLocationId(request.getLocationId());
        part.setStatus("ACTIVE");
        PartMasterEntity saved = partMasterRepository.save(partMasterMapper.toEntity(part));
        partCacheService.savePart(part);
        log.info("Created part: {}", saved.getPartCode());
        return PartResponseDTO.fromEntity(saved);
    }

    @Override
    @Transactional
    public PartResponseDTO updatePart(UUID id, PartUpdateRequestDTO request) throws SystemGlobalException {
        PartMasterEntity entity = partMasterRepository.findById(id)
                .orElseThrow(() -> new SystemGlobalException("Part not found: " + id, null));
        if (request.getPartName() != null) entity.setPartName(request.getPartName());
        if (request.getPartNameEn() != null) entity.setPartNameEn(request.getPartNameEn());
        if (request.getBrand() != null) entity.setBrand(request.getBrand());
        if (request.getModel() != null) entity.setModel(request.getModel());
        if (request.getUnitCost() != null) entity.setUnitCost(request.getUnitCost());
        if (request.getSellingPrice() != null) entity.setSellingPrice(request.getSellingPrice());
        if (request.getReorderLevel() != null) entity.setReorderLevel(request.getReorderLevel());
        if (request.getReorderQuantity() != null) entity.setReorderQuantity(request.getReorderQuantity());
        if (request.getMinStock() != null) entity.setMinStock(request.getMinStock());
        if (request.getMaxStock() != null) entity.setMaxStock(request.getMaxStock());
        if (request.getStatus() != null) entity.setStatus(request.getStatus());
        if (request.getLocationId() != null) entity.setLocationId(request.getLocationId());
        if (request.getDescription() != null) entity.setDescription(request.getDescription());
        PartMasterEntity saved = partMasterRepository.save(entity);
        partCacheService.evictPart(id);
        log.info("Updated part: {}", saved.getPartCode());
        return PartResponseDTO.fromEntity(saved);
    }

    @Override
    public PartResponseDTO getPartById(UUID id) throws SystemGlobalException {
        PartMasterEntity entity = partMasterRepository.findById(id)
                .orElseThrow(() -> new SystemGlobalException("Part not found: " + id, null));
        return PartResponseDTO.fromEntity(entity);
    }

    @Override
    public PartResponseDTO getPartByCode(String code) throws SystemGlobalException {
        PartMasterEntity entity = partMasterRepository.findByPartCode(code)
                .orElseThrow(() -> new SystemGlobalException("Part not found: " + code, null));
        return PartResponseDTO.fromEntity(entity);
    }

    @Override
    public Page<PartResponseDTO> getAllParts(int page, int size) throws SystemGlobalException {
        return partMasterRepository.findAll(PageRequest.of(page, size))
                .map(PartResponseDTO::fromEntity);
    }

    @Override
    public Page<PartResponseDTO> getLowStockParts(int page, int size) throws SystemGlobalException {
        List<PartMasterEntity> lowStock = partMasterRepository.findLowStockParts();
        int start = page * size;
        int end = Math.min(start + size, lowStock.size());
        if (start >= lowStock.size()) {
            return Page.empty();
        }
        List<PartResponseDTO> items = lowStock.subList(start, end).stream()
                .map(PartResponseDTO::fromEntity).toList();
        return new org.springframework.data.domain.PageImpl<>(items,
                PageRequest.of(page, size), lowStock.size());
    }

    @Override
    @Transactional
    public void deletePart(UUID id) throws SystemGlobalException {
        PartMasterEntity entity = partMasterRepository.findById(id)
                .orElseThrow(() -> new SystemGlobalException("Part not found: " + id, null));
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        partMasterRepository.save(entity);
        partCacheService.evictPart(id);
        log.info("Deleted part: {}", entity.getPartCode());
    }

    @Override
    public PartResponseDTO getPartByPartCode(String code) throws SystemGlobalException {
        return getPartByCode(code);
    }
}
