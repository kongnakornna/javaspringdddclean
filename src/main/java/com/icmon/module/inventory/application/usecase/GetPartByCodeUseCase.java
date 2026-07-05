package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.PartMasterService;
import com.icmon.module.inventory.presentation.dto.response.PartMasterResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetPartByCodeUseCase {
    private final PartMasterService partMasterService;
    public PartMasterResponseDTO execute(String partCode) throws SystemGlobalException {
        return partMasterService.getPartByCode(partCode);
    }
}
