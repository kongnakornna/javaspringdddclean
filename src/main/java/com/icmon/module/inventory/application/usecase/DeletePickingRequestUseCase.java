package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.PartPickingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeletePickingRequestUseCase {
    private final PartPickingService partPickingService;
    public void execute(UUID id) throws SystemGlobalException {
        partPickingService.deletePickingRequest(id);
    }
}
