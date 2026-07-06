package com.icmon.module.inventory.application.impl;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.StockLocationService;
import com.icmon.module.inventory.infrastructure.entity.StockLocationEntity;
import com.icmon.module.inventory.infrastructure.repository.StockLocationRepository;
import com.icmon.module.inventory.presentation.dto.request.StockLocationRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.StockLocationResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockLocationServiceImpl implements StockLocationService {
    private final StockLocationRepository locationRepository;

    @Override
    public List<StockLocationResponseDTO> getAllLocations() throws SystemGlobalException {
        return locationRepository.findAll().stream()
                .map(StockLocationResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public StockLocationResponseDTO createLocation(StockLocationRequestDTO request) throws SystemGlobalException {
        StockLocationEntity entity = new StockLocationEntity();
        entity.setLocationCode(request.getLocationCode());
        entity.setLocationName(request.getLocationName());
        entity.setLocationType(request.getLocationType());
        entity.setZone(request.getZone());
        entity.setCapacity(request.getCapacity());
        entity.setCurrentUsage(0);
        entity.setIsActive(true);
        entity.setWhitelabelId(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        StockLocationEntity saved = locationRepository.save(entity);
        log.info("Created location: {}", saved.getLocationCode());
        return StockLocationResponseDTO.fromEntity(saved);
    }
}
