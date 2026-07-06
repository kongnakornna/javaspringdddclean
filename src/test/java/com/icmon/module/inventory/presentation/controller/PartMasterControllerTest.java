package com.icmon.module.inventory.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icmon.module.inventory.application.interfaces.PartMasterService;
import com.icmon.module.inventory.presentation.dto.request.PartCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.PartResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PartMasterController.class)
@DisplayName("Part Master Controller Tests")
class PartMasterControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private PartMasterService partMasterService;

    @Test
    @DisplayName("should create part and return 201")
    void shouldCreatePart() throws Exception {
        PartCreateRequestDTO request = new PartCreateRequestDTO();
        request.setPartCode("TEST-001");
        request.setPartName("Test Part");

        PartResponseDTO response = PartResponseDTO.builder()
                .id(UUID.randomUUID()).partCode("TEST-001").partName("Test Part").build();

        when(partMasterService.createPart(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/parts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.partCode").value("TEST-001"));
    }

    @Test
    @DisplayName("should get part by ID and return 200")
    void shouldGetPartById() throws Exception {
        UUID id = UUID.randomUUID();
        PartResponseDTO response = PartResponseDTO.builder()
                .id(id).partCode("TEST-001").partName("Test Part").build();

        when(partMasterService.getPartById(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/parts/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.partCode").value("TEST-001"));
    }

    @Test
    @DisplayName("should return 204 on delete")
    void shouldDeletePart() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(delete("/api/v1/parts/{id}", id))
                .andExpect(status().isNoContent());
    }
}
