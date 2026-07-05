package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.PartMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeletePartUseCase {
    private final PartMasterService partMasterService;
    public void execute(UUID id) throws SystemGlobalException {
        partMasterService.deletePart(id);
    }
}
