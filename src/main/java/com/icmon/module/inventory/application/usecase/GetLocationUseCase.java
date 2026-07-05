package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.StockLocationService;
import com.icmon.module.inventory.presentation.dto.response.StockLocationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetLocationUseCase {
    private final StockLocationService stockLocationService;
    public StockLocationResponseDTO execute(UUID id) throws SystemGlobalException {
        return stockLocationService.getLocation(id);
    }
}
