package com.icmon.module.weborder.application.interfaces;

import com.icmon.module.weborder.presentation.dto.response.CatalogueItemResponseDTO;
import com.icmon.module.weborder.presentation.dto.response.CatalogueCategoryResponseDTO;
import com.icmon.exception.SystemGlobalException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CatalogueService {
    Page<CatalogueItemResponseDTO> listItems(Pageable pageable) throws SystemGlobalException;

    CatalogueItemResponseDTO getItem(UUID id) throws SystemGlobalException;

    Page<CatalogueItemResponseDTO> getItemsByCategory(UUID categoryId, Pageable pageable) throws SystemGlobalException;

    Page<CatalogueItemResponseDTO> searchItems(String keyword, Pageable pageable) throws SystemGlobalException;

    List<CatalogueItemResponseDTO> getFeaturedItems() throws SystemGlobalException;

    List<CatalogueCategoryResponseDTO> getCategories() throws SystemGlobalException;
}
