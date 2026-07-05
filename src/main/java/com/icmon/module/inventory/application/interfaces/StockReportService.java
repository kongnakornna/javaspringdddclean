package com.icmon.module.inventory.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.presentation.dto.response.StockReportResponseDTO;

import java.util.UUID;

public interface StockReportService {
    byte[] generateStockReport() throws SystemGlobalException;
    byte[] generatePartStockReport(UUID partId) throws SystemGlobalException;
    byte[] generateInventoryTransactionReport(UUID partId) throws SystemGlobalException;
    byte[] generateLowStockReport() throws SystemGlobalException;
}
