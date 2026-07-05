package com.icmon.module.inventory.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.presentation.dto.request.PartMasterCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.request.PartMasterUpdateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.PartMasterResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PartMasterService {
    PartMasterResponseDTO createPart(PartMasterCreateRequestDTO request) throws SystemGlobalException;
    PartMasterResponseDTO getPart(UUID id) throws SystemGlobalException;
    PartMasterResponseDTO getPartByCode(String partCode) throws SystemGlobalException;
    Page<PartMasterResponseDTO> searchParts(String search, UUID categoryId, String status, Pageable pageable) throws SystemGlobalException;
    PartMasterResponseDTO updatePart(UUID id, PartMasterUpdateRequestDTO request) throws SystemGlobalException;
    void deletePart(UUID id) throws SystemGlobalException;
    PartMasterResponseDTO updateStockQuantity(UUID id, int quantity) throws SystemGlobalException;
}
