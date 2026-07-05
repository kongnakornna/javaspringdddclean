package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.StockLocationService;
import com.icmon.module.inventory.presentation.dto.request.StockLocationCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.StockLocationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateLocationUseCase {
    private final StockLocationService stockLocationService;
    public StockLocationResponseDTO execute(StockLocationCreateRequestDTO request) throws SystemGlobalException {
        return stockLocationService.createLocation(request);
    }
}
