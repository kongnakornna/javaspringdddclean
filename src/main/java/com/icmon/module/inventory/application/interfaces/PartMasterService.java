package com.icmon.module.inventory.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.presentation.dto.request.PartCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.request.PartUpdateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.PartResponseDTO;
import org.springframework.data.domain.Page;
import java.util.UUID;

public interface PartMasterService {
    PartResponseDTO createPart(PartCreateRequestDTO request) throws SystemGlobalException;
    PartResponseDTO updatePart(UUID id, PartUpdateRequestDTO request) throws SystemGlobalException;
    PartResponseDTO getPartById(UUID id) throws SystemGlobalException;
    PartResponseDTO getPartByCode(String code) throws SystemGlobalException;
    Page<PartResponseDTO> getAllParts(int page, int size) throws SystemGlobalException;
    Page<PartResponseDTO> getLowStockParts(int page, int size) throws SystemGlobalException;
    void deletePart(UUID id) throws SystemGlobalException;
    PartResponseDTO getPartByPartCode(String code) throws SystemGlobalException;
}
