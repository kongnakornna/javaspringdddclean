package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.StockReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenerateStockReportUseCase {
    private final StockReportService stockReportService;
    public byte[] execute() throws SystemGlobalException {
        return stockReportService.generateStockReport();
    }
}
