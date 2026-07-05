package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.PartPickingService;
import com.icmon.module.inventory.presentation.dto.response.PickingResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PickItemsUseCase {
    private final PartPickingService partPickingService;
    public PickingResponseDTO execute(UUID id) throws SystemGlobalException {
        return partPickingService.pickItems(id);
    }
}
