package com.icmon.module.email.presentation;

import com.icmon.module.email.application.interfaces.EmailHistoryService;
import com.icmon.module.email.presentation.controller.EmailHistoryController;
import com.icmon.module.email.presentation.dto.response.EmailHistoryResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EmailHistoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmailHistoryService emailHistoryService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new EmailHistoryController(emailHistoryService))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
    }

    @Test
    void shouldGetHistoryById() throws Exception {
        UUID id = UUID.randomUUID();
        EmailHistoryResponseDTO response = new EmailHistoryResponseDTO();
        response.setId(id);
        response.setEmailId("EMAIL-20260705-0001");
        response.setToEmail("test@example.com");
        response.setSubject("Test");
        response.setStatus("SENT");

        when(emailHistoryService.getHistoryById(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/email/history/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emailId").value("EMAIL-20260705-0001"))
                .andExpect(jsonPath("$.status").value("SENT"));
    }

    @Test
    void shouldGetHistoryByEmailId() throws Exception {
        String emailId = "EMAIL-20260705-0001";
        EmailHistoryResponseDTO response = new EmailHistoryResponseDTO();
        response.setId(UUID.randomUUID());
        response.setEmailId(emailId);
        response.setToEmail("test@example.com");
        response.setSubject("Test");
        response.setStatus("SENT");

        when(emailHistoryService.getHistoryByEmailId(emailId)).thenReturn(response);

        mockMvc.perform(get("/api/v1/email/history/email/{emailId}", emailId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emailId").value(emailId))
                .andExpect(jsonPath("$.status").value("SENT"));
    }

    @Test
    void shouldSearchHistories() throws Exception {
        EmailHistoryResponseDTO response = new EmailHistoryResponseDTO();
        response.setId(UUID.randomUUID());
        response.setEmailId("EMAIL-20260705-0001");
        response.setToEmail("test@example.com");
        response.setSubject("Test");
        response.setStatus("SENT");

        Page<EmailHistoryResponseDTO> page = new PageImpl<>(List.of(response), PageRequest.of(0, 20), 1);
        when(emailHistoryService.searchHistories(any(), any(), any(), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/email/history")
                        .param("status", "SENT")
                        .param("toEmail", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].emailId").value("EMAIL-20260705-0001"))
                .andExpect(jsonPath("$.content[0].status").value("SENT"));
    }
}
