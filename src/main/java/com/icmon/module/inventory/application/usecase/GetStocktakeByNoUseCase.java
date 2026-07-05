package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.StocktakeService;
import com.icmon.module.inventory.presentation.dto.response.StocktakeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetStocktakeByNoUseCase {
    private final StocktakeService stocktakeService;
    public StocktakeResponseDTO execute(String stocktakeNo) throws SystemGlobalException {
        return stocktakeService.getStocktakeByNo(stocktakeNo);
    }
}
