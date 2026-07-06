package com.icmon.module.inventory.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icmon.module.inventory.application.interfaces.PartPickingService;
import com.icmon.module.inventory.presentation.dto.request.PickingCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.PickingResponseDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PartPickingController.class)
@DisplayName("Part Picking Controller Tests")
class PartPickingControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private PartPickingService partPickingService;

    @Test
    @DisplayName("should create picking and return 201")
    void shouldCreatePicking() throws Exception {
        PickingCreateRequestDTO request = new PickingCreateRequestDTO();
        request.setJobId(UUID.randomUUID());

        when(partPickingService.createPicking(any())).thenReturn(PickingResponseDTO.builder().build());

        mockMvc.perform(post("/api/v1/part-picking")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
