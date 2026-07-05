package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.StocktakeService;
import com.icmon.module.inventory.presentation.dto.request.StocktakeCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.StocktakeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateStocktakeUseCase {
    private final StocktakeService stocktakeService;
    public StocktakeResponseDTO execute(StocktakeCreateRequestDTO request) throws SystemGlobalException {
        return stocktakeService.createStocktake(request);
    }
}
