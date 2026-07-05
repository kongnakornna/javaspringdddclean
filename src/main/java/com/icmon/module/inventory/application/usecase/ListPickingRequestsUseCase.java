package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.PartPickingService;
import com.icmon.module.inventory.presentation.dto.response.PickingResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ListPickingRequestsUseCase {
    private final PartPickingService partPickingService;
    public Page<PickingResponseDTO> execute(String status, UUID jobId, Pageable pageable) throws SystemGlobalException {
        return partPickingService.listPickingRequests(status, jobId, pageable);
    }
}
