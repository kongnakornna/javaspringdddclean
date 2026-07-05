package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.StocktakeService;
import com.icmon.module.inventory.presentation.dto.response.StocktakeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CompleteStocktakeUseCase {
    private final StocktakeService stocktakeService;
    public StocktakeResponseDTO execute(UUID id) throws SystemGlobalException {
        return stocktakeService.completeStocktake(id);
    }
}
