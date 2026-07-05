package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.PartMasterService;
import com.icmon.module.inventory.presentation.dto.response.PartMasterResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateStockQuantityUseCase {
    private final PartMasterService partMasterService;
    public PartMasterResponseDTO execute(UUID id, int quantity) throws SystemGlobalException {
        return partMasterService.updateStockQuantity(id, quantity);
    }
}
