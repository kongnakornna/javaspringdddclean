package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.StocktakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteStocktakeUseCase {
    private final StocktakeService stocktakeService;
    public void execute(UUID id) throws SystemGlobalException {
        stocktakeService.deleteStocktake(id);
    }
}
