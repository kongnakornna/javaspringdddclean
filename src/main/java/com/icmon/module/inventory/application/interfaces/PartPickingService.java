package com.icmon.module.inventory.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.presentation.dto.request.PickingCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.PickingResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PartPickingService {
    PickingResponseDTO createPickingRequest(PickingCreateRequestDTO request) throws SystemGlobalException;
    PickingResponseDTO getPickingRequest(UUID id) throws SystemGlobalException;
    PickingResponseDTO getPickingRequestByNo(String pickingNo) throws SystemGlobalException;
    Page<PickingResponseDTO> listPickingRequests(String status, UUID jobId, Pageable pageable) throws SystemGlobalException;
    PickingResponseDTO pickItems(UUID id) throws SystemGlobalException;
    PickingResponseDTO confirmPicking(UUID id) throws SystemGlobalException;
    void deletePickingRequest(UUID id) throws SystemGlobalException;
}
