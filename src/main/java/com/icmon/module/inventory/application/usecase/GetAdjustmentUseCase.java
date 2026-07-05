package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.InventoryAdjustmentService;
import com.icmon.module.inventory.presentation.dto.response.AdjustmentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetAdjustmentUseCase {
    private final InventoryAdjustmentService adjustmentService;
    public AdjustmentResponseDTO execute(UUID id) throws SystemGlobalException {
        return adjustmentService.getAdjustment(id);
    }
}
