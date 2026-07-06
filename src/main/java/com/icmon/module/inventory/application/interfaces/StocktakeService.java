package com.icmon.module.inventory.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.presentation.dto.request.StockTakeRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.StockTakeResponseDTO;
import java.util.UUID;

public interface StockTakeService {
    StockTakeResponseDTO createStockTake(StockTakeRequestDTO request) throws SystemGlobalException;
    StockTakeResponseDTO completeStockTake(UUID id) throws SystemGlobalException;
}
