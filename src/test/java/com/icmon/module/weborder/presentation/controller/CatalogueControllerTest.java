package com.icmon.module.weborder.presentation.controller;

import com.icmon.module.weborder.application.interfaces.CatalogueService;
import com.icmon.module.weborder.presentation.dto.response.CatalogueItemResponseDTO;
import com.icmon.exception.SystemGlobalException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CatalogueController.class)
class CatalogueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatalogueService catalogueService;

    @Test
    void testListCatalogueItems() throws Exception {
        CatalogueItemResponseDTO dto = CatalogueItemResponseDTO.builder()
                .id(UUID.randomUUID())
                .itemCode("BRK-001")
                .itemName("ผ้าเบรกหน้า")
                .build();
        Page<CatalogueItemResponseDTO> page = new PageImpl<>(List.of(dto));
        when(catalogueService.listItems(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/wos/catalogue"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetItem() throws Exception {
        CatalogueItemResponseDTO dto = CatalogueItemResponseDTO.builder()
                .id(UUID.randomUUID())
                .itemCode("BRK-001")
                .build();
        when(catalogueService.getItem(any(UUID.class))).thenReturn(dto);

        mockMvc.perform(get("/api/v1/wos/catalogue/{id}", UUID.randomUUID()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetCategories() throws Exception {
        when(catalogueService.getCategories()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/wos/catalogue/categories"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetFeaturedItems() throws Exception {
        when(catalogueService.getFeaturedItems()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/wos/catalogue/featured"))
                .andExpect(status().isOk());
    }

    @Test
    void testSearchItems() throws Exception {
        Page<CatalogueItemResponseDTO> page = new PageImpl<>(List.of());
        when(catalogueService.searchItems(any(String.class), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/wos/catalogue/search").param("keyword", "brake"))
                .andExpect(status().isOk());
    }
}
