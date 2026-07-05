package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.PartPickingService;
import com.icmon.module.inventory.presentation.dto.response.PickingResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetPickingRequestByNoUseCase {
    private final PartPickingService partPickingService;
    public PickingResponseDTO execute(String pickingNo) throws SystemGlobalException {
        return partPickingService.getPickingRequestByNo(pickingNo);
    }
}
