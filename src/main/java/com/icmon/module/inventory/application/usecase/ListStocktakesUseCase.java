package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.StocktakeService;
import com.icmon.module.inventory.presentation.dto.response.StocktakeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListStocktakesUseCase {
    private final StocktakeService stocktakeService;
    public Page<StocktakeResponseDTO> execute(String status, Pageable pageable) throws SystemGlobalException {
        return stocktakeService.listStocktakes(status, pageable);
    }
}
