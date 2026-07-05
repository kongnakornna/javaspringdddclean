package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.InventoryAdjustmentService;
import com.icmon.module.inventory.presentation.dto.response.AdjustmentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAdjustmentByNoUseCase {
    private final InventoryAdjustmentService adjustmentService;
    public AdjustmentResponseDTO execute(String adjustmentNo) throws SystemGlobalException {
        return adjustmentService.getAdjustmentByNo(adjustmentNo);
    }
}
