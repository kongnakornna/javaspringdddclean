package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.InventoryAdjustmentService;
import com.icmon.module.inventory.presentation.dto.request.AdjustmentCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.AdjustmentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateAdjustmentUseCase {
    private final InventoryAdjustmentService adjustmentService;
    public AdjustmentResponseDTO execute(AdjustmentCreateRequestDTO request) throws SystemGlobalException {
        return adjustmentService.createAdjustment(request);
    }
}
