package com.icmon.module.inventory.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.presentation.dto.request.StockLocationRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.StockLocationResponseDTO;
import java.util.List;

public interface StockLocationService {
    List<StockLocationResponseDTO> getAllLocations() throws SystemGlobalException;
    StockLocationResponseDTO createLocation(StockLocationRequestDTO request) throws SystemGlobalException;
}
