package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.StockLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteLocationUseCase {
    private final StockLocationService stockLocationService;
    public void execute(UUID id) throws SystemGlobalException {
        stockLocationService.deleteLocation(id);
    }
}
