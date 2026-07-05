package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.PartMasterService;
import com.icmon.module.inventory.presentation.dto.request.PartMasterUpdateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.PartMasterResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdatePartUseCase {
    private final PartMasterService partMasterService;
    public PartMasterResponseDTO execute(UUID id, PartMasterUpdateRequestDTO request) throws SystemGlobalException {
        return partMasterService.updatePart(id, request);
    }
}
