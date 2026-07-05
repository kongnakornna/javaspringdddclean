package com.icmon.module.inventory.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.presentation.dto.request.StockLocationCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.request.StockLocationUpdateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.StockLocationResponseDTO;

import java.util.List;
import java.util.UUID;

public interface StockLocationService {
    StockLocationResponseDTO createLocation(StockLocationCreateRequestDTO request) throws SystemGlobalException;
    StockLocationResponseDTO getLocation(UUID id) throws SystemGlobalException;
    List<StockLocationResponseDTO> getAllActiveLocations() throws SystemGlobalException;
    StockLocationResponseDTO updateLocation(UUID id, StockLocationUpdateRequestDTO request) throws SystemGlobalException;
    void deleteLocation(UUID id) throws SystemGlobalException;
}
