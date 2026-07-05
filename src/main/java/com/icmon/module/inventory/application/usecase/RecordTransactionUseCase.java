package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.InventoryService;
import com.icmon.module.inventory.presentation.dto.request.InventoryTransactionRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.InventoryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecordTransactionUseCase {
    private final InventoryService inventoryService;
    public InventoryResponseDTO execute(InventoryTransactionRequestDTO request) throws SystemGlobalException {
        return inventoryService.recordTransaction(request);
    }
}
