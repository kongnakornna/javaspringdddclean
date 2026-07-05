package com.icmon.module.inventory.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.inventory.application.interfaces.StockLocationService;
import com.icmon.module.inventory.infrastructure.cache.StockLocationCacheService;
import com.icmon.module.inventory.infrastructure.entity.StockLocationEntity;
import com.icmon.module.inventory.infrastructure.repository.StockLocationRepository;
import com.icmon.module.inventory.presentation.dto.request.StockLocationCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.request.StockLocationUpdateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.StockLocationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockLocationServiceImpl extends GenericAuthDomainServiceImpl implements StockLocationService {

    private final StockLocationRepository repository;
    private final StockLocationCacheService cacheService;

    @Override
    @Transactional
    public StockLocationResponseDTO createLocation(StockLocationCreateRequestDTO request) throws SystemGlobalException {
        if (repository.findByLocationCode(request.getLocationCode()).isPresent()) {
            throw new FailedRequestException("Location code already exists: " + request.getLocationCode(), null);
        }
        StockLocationEntity entity = new StockLocationEntity();
        entity.setLocationCode(request.getLocationCode());
        entity.setLocationName(request.getLocationName());
        entity.setLocationType(request.getLocationType() != null ? request.getLocationType() : "SHELF");
        entity.setZone(request.getZone());
        entity.setCapacity(request.getCapacity());
        entity.setCurrentUsage(0);
        entity.setActive(true);
        entity.setNotes(request.getNotes());
        StockLocationEntity saved = repository.save(entity);
        cacheService.cache(saved);
        return toResponseDTO(saved);
    }

    @Override
    public StockLocationResponseDTO getLocation(UUID id) throws SystemGlobalException {
        StockLocationEntity entity = repository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Location not found with id: " + id, null));
        return toResponseDTO(entity);
    }

    @Override
    public List<StockLocationResponseDTO> getAllActiveLocations() throws SystemGlobalException {
        return repository.findByIsActiveTrue().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StockLocationResponseDTO updateLocation(UUID id, StockLocationUpdateRequestDTO request) throws SystemGlobalException {
        StockLocationEntity entity = repository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Location not found with id: " + id, null));
        if (request.getLocationName() != null) entity.setLocationName(request.getLocationName());
        if (request.getLocationType() != null) entity.setLocationType(request.getLocationType());
        if (request.getZone() != null) entity.setZone(request.getZone());
        if (request.getCapacity() != null) entity.setCapacity(request.getCapacity());
        if (request.getIsActive() != null) entity.setActive(request.getIsActive());
        if (request.getNotes() != null) entity.setNotes(request.getNotes());
        StockLocationEntity saved = repository.save(entity);
        cacheService.evict(id);
        cacheService.cache(saved);
        return toResponseDTO(saved);
    }

    @Override
    @Transactional
    public void deleteLocation(UUID id) throws SystemGlobalException {
        StockLocationEntity entity = repository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Location not found with id: " + id, null));
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        repository.save(entity);
        cacheService.evict(id);
    }

    private StockLocationResponseDTO toResponseDTO(StockLocationEntity entity) {
        StockLocationResponseDTO dto = new StockLocationResponseDTO();
        dto.setId(entity.getId());
        dto.setLocationCode(entity.getLocationCode());
        dto.setLocationName(entity.getLocationName());
        dto.setLocationType(entity.getLocationType());
        dto.setZone(entity.getZone());
        dto.setCapacity(entity.getCapacity());
        dto.setCurrentUsage(entity.getCurrentUsage());
        dto.setActive(entity.isActive());
        dto.setNotes(entity.getNotes());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
