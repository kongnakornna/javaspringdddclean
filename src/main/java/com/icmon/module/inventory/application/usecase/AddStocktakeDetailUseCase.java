package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.StocktakeService;
import com.icmon.module.inventory.presentation.dto.request.StocktakeDetailRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.StocktakeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AddStocktakeDetailUseCase {
    private final StocktakeService stocktakeService;
    public StocktakeResponseDTO execute(UUID stocktakeId, StocktakeDetailRequestDTO request) throws SystemGlobalException {
        return stocktakeService.addDetail(stocktakeId, request);
    }
}
