package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.PartMasterService;
import com.icmon.module.inventory.presentation.dto.response.PartMasterResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SearchPartsUseCase {
    private final PartMasterService partMasterService;
    public Page<PartMasterResponseDTO> execute(String search, UUID categoryId, String status, Pageable pageable) throws SystemGlobalException {
        return partMasterService.searchParts(search, categoryId, status, pageable);
    }
}
