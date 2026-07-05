package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.StockReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GeneratePartStockReportUseCase {
    private final StockReportService stockReportService;
    public byte[] execute(UUID partId) throws SystemGlobalException {
        return stockReportService.generatePartStockReport(partId);
    }
}
