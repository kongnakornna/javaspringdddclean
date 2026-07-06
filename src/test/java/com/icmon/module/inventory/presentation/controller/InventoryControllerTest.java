package com.icmon.module.inventory.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icmon.module.inventory.application.interfaces.InventoryService;
import com.icmon.module.inventory.presentation.dto.request.InventoryReceiveRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.InventoryResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InventoryController.class)
@DisplayName("Inventory Controller Tests")
class InventoryControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private InventoryService inventoryService;

    @Test
    @DisplayName("should receive goods and return 201")
    void shouldReceiveGoods() throws Exception {
        InventoryReceiveRequestDTO request = new InventoryReceiveRequestDTO();
        request.setPartId(UUID.randomUUID());
        request.setQuantity(10);
        request.setUnitCost(new BigDecimal("100.00"));

        when(inventoryService.receiveGoods(any())).thenReturn(InventoryResponseDTO.builder().build());

        mockMvc.perform(post("/api/v1/inventory/receive")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
