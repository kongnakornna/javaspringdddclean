package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.InventoryAdjustmentService;
import com.icmon.module.inventory.presentation.dto.response.AdjustmentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListAdjustmentsUseCase {
    private final InventoryAdjustmentService adjustmentService;
    public Page<AdjustmentResponseDTO> execute(String status, String type, Pageable pageable) throws SystemGlobalException {
        return adjustmentService.listAdjustments(status, type, pageable);
    }
}
