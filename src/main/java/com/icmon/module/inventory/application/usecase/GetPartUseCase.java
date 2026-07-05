package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.PartMasterService;
import com.icmon.module.inventory.presentation.dto.response.PartMasterResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetPartUseCase {
    private final PartMasterService partMasterService;
    public PartMasterResponseDTO execute(UUID id) throws SystemGlobalException {
        return partMasterService.getPart(id);
    }
}
