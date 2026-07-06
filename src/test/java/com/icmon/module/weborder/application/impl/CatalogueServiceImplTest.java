package com.icmon.module.weborder.application.impl;

import com.icmon.module.weborder.infrastructure.entity.CatalogueCategoryEntity;
import com.icmon.module.weborder.infrastructure.entity.CatalogueItemEntity;
import com.icmon.module.weborder.infrastructure.repository.CatalogueCategoryRepository;
import com.icmon.module.weborder.infrastructure.repository.CatalogueItemRepository;
import com.icmon.module.weborder.presentation.dto.response.CatalogueItemResponseDTO;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatalogueServiceImplTest {

    @Mock
    private CatalogueItemRepository itemRepository;
    @Mock
    private CatalogueCategoryRepository categoryRepository;

    private CatalogueServiceImpl catalogueService;

    @BeforeEach
    void setUp() {
        catalogueService = new CatalogueServiceImpl(itemRepository, categoryRepository, null, null);
    }

    @Test
    void testListItems() throws SystemGlobalException {
        CatalogueItemEntity entity = new CatalogueItemEntity();
        entity.setId(UUID.randomUUID());
        entity.setItemCode("BRK-001");
        entity.setItemName("ผ้าเบรกหน้า");
        entity.setIsActive(true);

        when(itemRepository.findByDeletedFalse(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(entity)));

        Page<CatalogueItemResponseDTO> result = catalogueService.listItems(Pageable.unpaged());
        assertEquals(1, result.getContent().size());
        assertEquals("BRK-001", result.getContent().get(0).getItemCode());
    }

    @Test
    void testGetItem() throws SystemGlobalException {
        UUID id = UUID.randomUUID();
        CatalogueItemEntity entity = new CatalogueItemEntity();
        entity.setId(id);
        entity.setItemCode("BRK-001");

        when(itemRepository.findById(id)).thenReturn(Optional.of(entity));

        CatalogueItemResponseDTO result = catalogueService.getItem(id);
        assertEquals("BRK-001", result.getItemCode());
    }

    @Test
    void testGetItemNotFound() {
        UUID id = UUID.randomUUID();
        when(itemRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(FailedRequestException.class, () -> catalogueService.getItem(id));
    }

    @Test
    void testGetCategories() throws SystemGlobalException {
        CatalogueCategoryEntity entity = new CatalogueCategoryEntity();
        entity.setId(UUID.randomUUID());
        entity.setCategoryCode("BRAKE");
        entity.setCategoryName("ระบบเบรก");

        when(categoryRepository.findByIsActiveTrueAndDeletedFalseOrderBySortOrder())
                .thenReturn(List.of(entity));

        var result = catalogueService.getCategories();
        assertEquals(1, result.size());
        assertEquals("BRAKE", result.get(0).getCategoryCode());
    }
}
