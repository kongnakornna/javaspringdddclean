package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.PartPickingService;
import com.icmon.module.inventory.presentation.dto.request.PickingCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.PickingResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreatePickingRequestUseCase {
    private final PartPickingService partPickingService;
    public PickingResponseDTO execute(PickingCreateRequestDTO request) throws SystemGlobalException {
        return partPickingService.createPickingRequest(request);
    }
}
