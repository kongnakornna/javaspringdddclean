package com.icmon.module.inventory.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.presentation.dto.request.AdjustmentRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.AdjustmentResponseDTO;
import java.util.UUID;

public interface StockAdjustmentService {
    AdjustmentResponseDTO createAdjustment(AdjustmentRequestDTO request) throws SystemGlobalException;
    AdjustmentResponseDTO approveAdjustment(UUID id) throws SystemGlobalException;
}
