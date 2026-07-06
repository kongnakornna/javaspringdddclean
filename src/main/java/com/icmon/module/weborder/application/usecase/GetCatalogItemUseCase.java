package com.icmon.module.weborder.application.usecase;

import com.icmon.module.weborder.application.interfaces.CatalogueService;
import com.icmon.module.weborder.presentation.dto.response.CatalogueItemResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetCatalogItemUseCase {
    private final CatalogueService catalogueService;

    public CatalogueItemResponseDTO execute(UUID id) throws SystemGlobalException {
        return catalogueService.getItem(id);
    }
}
