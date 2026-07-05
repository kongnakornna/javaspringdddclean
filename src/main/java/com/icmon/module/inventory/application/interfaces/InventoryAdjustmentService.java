package com.icmon.module.inventory.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.presentation.dto.request.AdjustmentCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.request.AdjustmentApproveRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.AdjustmentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface InventoryAdjustmentService {
    AdjustmentResponseDTO createAdjustment(AdjustmentCreateRequestDTO request) throws SystemGlobalException;
    AdjustmentResponseDTO getAdjustment(UUID id) throws SystemGlobalException;
    AdjustmentResponseDTO getAdjustmentByNo(String adjustmentNo) throws SystemGlobalException;
    Page<AdjustmentResponseDTO> listAdjustments(String status, String type, Pageable pageable) throws SystemGlobalException;
    AdjustmentResponseDTO approveAdjustment(UUID id, AdjustmentApproveRequestDTO request) throws SystemGlobalException;
    void deleteAdjustment(UUID id) throws SystemGlobalException;
}
