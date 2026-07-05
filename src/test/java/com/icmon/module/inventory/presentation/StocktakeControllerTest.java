package com.icmon.module.inventory.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icmon.module.inventory.application.usecase.*;
import com.icmon.module.inventory.presentation.controller.StocktakeController;
import com.icmon.module.inventory.presentation.dto.request.StocktakeCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.StocktakeResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class StocktakeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CreateStocktakeUseCase createStocktakeUseCase;
    @Mock
    private GetStocktakeUseCase getStocktakeUseCase;
    @Mock
    private GetStocktakeByNoUseCase getStocktakeByNoUseCase;
    @Mock
    private ListStocktakesUseCase listStocktakesUseCase;
    @Mock
    private AddStocktakeDetailUseCase addStocktakeDetailUseCase;
    @Mock
    private CompleteStocktakeUseCase completeStocktakeUseCase;
    @Mock
    private DeleteStocktakeUseCase deleteStocktakeUseCase;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new StocktakeController(
                createStocktakeUseCase, getStocktakeUseCase, getStocktakeByNoUseCase,
                listStocktakesUseCase, addStocktakeDetailUseCase, completeStocktakeUseCase,
                deleteStocktakeUseCase
        )).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldCreateStocktake() throws Exception {
        StocktakeCreateRequestDTO request = new StocktakeCreateRequestDTO();
        request.setStartedBy(UUID.randomUUID());

        StocktakeResponseDTO response = new StocktakeResponseDTO();
        response.setId(UUID.randomUUID());
        response.setStocktakeNo("ST-2024-0001");
        response.setStatus("DRAFT");

        when(createStocktakeUseCase.execute(any(StocktakeCreateRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/stocktakes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.stocktakeNo").value("ST-2024-0001"));
    }

    @Test
    void shouldGetStocktakeById() throws Exception {
        UUID id = UUID.randomUUID();
        StocktakeResponseDTO response = new StocktakeResponseDTO();
        response.setId(id);
        response.setStocktakeNo("ST-2024-0001");

        when(getStocktakeUseCase.execute(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/stocktakes/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stocktakeNo").value("ST-2024-0001"));
    }

    @Test
    void shouldCompleteStocktake() throws Exception {
        UUID id = UUID.randomUUID();
        StocktakeResponseDTO response = new StocktakeResponseDTO();
        response.setId(id);
        response.setStatus("COMPLETED");
        response.setCompletedAt(LocalDateTime.now());

        when(completeStocktakeUseCase.execute(id)).thenReturn(response);

        mockMvc.perform(put("/api/v1/stocktakes/{id}/complete", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }
}
