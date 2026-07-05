package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.StockLocationService;
import com.icmon.module.inventory.presentation.dto.response.StockLocationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListActiveLocationsUseCase {
    private final StockLocationService stockLocationService;
    public List<StockLocationResponseDTO> execute() throws SystemGlobalException {
        return stockLocationService.getAllActiveLocations();
    }
}
