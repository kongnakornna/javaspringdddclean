package com.icmon.module.inventory.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.presentation.dto.request.PickingCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.PickingResponseDTO;
import java.util.List;
import java.util.UUID;

public interface PartPickingService {
    PickingResponseDTO createPicking(PickingCreateRequestDTO request) throws SystemGlobalException;
    PickingResponseDTO getPickingById(UUID id) throws SystemGlobalException;
    PickingResponseDTO confirmPicking(UUID id) throws SystemGlobalException;
    List<PickingResponseDTO> getPickingByJobId(UUID jobId) throws SystemGlobalException;
}
