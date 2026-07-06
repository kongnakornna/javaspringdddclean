package com.icmon.module.weborder.application.usecase;

import com.icmon.module.weborder.application.interfaces.CatalogueService;
import com.icmon.module.weborder.presentation.dto.response.CatalogueItemResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListCatalogUseCase {
    private final CatalogueService catalogueService;

    public Page<CatalogueItemResponseDTO> execute(Pageable pageable) throws SystemGlobalException {
        return catalogueService.listItems(pageable);
    }
}
