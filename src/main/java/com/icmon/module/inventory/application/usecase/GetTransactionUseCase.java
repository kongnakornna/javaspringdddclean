package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.InventoryService;
import com.icmon.module.inventory.presentation.dto.response.InventoryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetTransactionUseCase {
    private final InventoryService inventoryService;
    public InventoryResponseDTO execute(UUID id) throws SystemGlobalException {
        return inventoryService.getTransaction(id);
    }
}
