package com.icmon.module.inventory.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icmon.module.inventory.application.usecase.*;
import com.icmon.module.inventory.presentation.controller.PartMasterController;
import com.icmon.module.inventory.presentation.dto.request.PartMasterCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.PartMasterResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PartMasterControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CreatePartUseCase createPartUseCase;
    @Mock
    private GetPartUseCase getPartUseCase;
    @Mock
    private GetPartByCodeUseCase getPartByCodeUseCase;
    @Mock
    private SearchPartsUseCase searchPartsUseCase;
    @Mock
    private UpdatePartUseCase updatePartUseCase;
    @Mock
    private DeletePartUseCase deletePartUseCase;
    @Mock
    private UpdateStockQuantityUseCase updateStockQuantityUseCase;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new PartMasterController(
                createPartUseCase, getPartUseCase, getPartByCodeUseCase,
                searchPartsUseCase, updatePartUseCase, deletePartUseCase,
                updateStockQuantityUseCase
        )).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldCreatePart() throws Exception {
        PartMasterCreateRequestDTO request = new PartMasterCreateRequestDTO();
        request.setPartCode("BRK-001");
        request.setPartName("Brake Pad");

        PartMasterResponseDTO response = new PartMasterResponseDTO();
        response.setId(UUID.randomUUID());
        response.setPartCode("BRK-001");

        when(createPartUseCase.execute(any(PartMasterCreateRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/parts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.partCode").value("BRK-001"));
    }

    @Test
    void shouldGetPartById() throws Exception {
        UUID id = UUID.randomUUID();
        PartMasterResponseDTO response = new PartMasterResponseDTO();
        response.setId(id);
        response.setPartCode("BRK-001");

        when(getPartUseCase.execute(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/parts/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.partCode").value("BRK-001"));
    }

    @Test
    void shouldDeletePart() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(delete("/api/v1/parts/{id}", id))
                .andExpect(status().isNoContent());
    }
}
