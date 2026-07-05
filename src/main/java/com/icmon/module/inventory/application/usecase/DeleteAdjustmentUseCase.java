package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.InventoryAdjustmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteAdjustmentUseCase {
    private final InventoryAdjustmentService adjustmentService;
    public void execute(UUID id) throws SystemGlobalException {
        adjustmentService.deleteAdjustment(id);
    }
}
