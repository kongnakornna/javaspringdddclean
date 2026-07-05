package com.icmon.module.email.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icmon.module.email.application.interfaces.EmailTemplateService;
import com.icmon.module.email.presentation.controller.EmailTemplateController;
import com.icmon.module.email.presentation.dto.request.EmailTemplateRequestDTO;
import com.icmon.module.email.presentation.dto.response.EmailTemplateResponseDTO;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EmailTemplateControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmailTemplateService emailTemplateService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new EmailTemplateController(emailTemplateService))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldCreateTemplate() throws Exception {
        EmailTemplateRequestDTO request = new EmailTemplateRequestDTO();
        request.setTemplateCode("WELCOME_EMAIL");
        request.setTemplateName("Welcome");
        request.setSubject("Hello");
        request.setCategory("NOTIFICATION");

        EmailTemplateResponseDTO response = new EmailTemplateResponseDTO();
        response.setId(UUID.randomUUID());
        response.setTemplateCode("WELCOME_EMAIL");
        response.setTemplateName("Welcome");
        response.setSubject("Hello");

        when(emailTemplateService.createTemplate(any(EmailTemplateRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/email/templates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.templateCode").value("WELCOME_EMAIL"))
                .andExpect(jsonPath("$.templateName").value("Welcome"));
    }

    @Test
    void shouldGetTemplate() throws Exception {
        EmailTemplateResponseDTO response = new EmailTemplateResponseDTO();
        response.setId(UUID.randomUUID());
        response.setTemplateCode("WELCOME_EMAIL");
        response.setSubject("Hello");

        when(emailTemplateService.getTemplate("WELCOME_EMAIL", "th")).thenReturn(response);

        mockMvc.perform(get("/api/v1/email/templates/{templateCode}", "WELCOME_EMAIL")
                        .param("language", "th"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.templateCode").value("WELCOME_EMAIL"))
                .andExpect(jsonPath("$.subject").value("Hello"));
    }

    @Test
    void shouldListTemplates() throws Exception {
        EmailTemplateResponseDTO response = new EmailTemplateResponseDTO();
        response.setId(UUID.randomUUID());
        response.setTemplateCode("WELCOME_EMAIL");

        Page<EmailTemplateResponseDTO> page = new PageImpl<>(List.of(response), PageRequest.of(0, 20), 1);
        when(emailTemplateService.listTemplates(any(), any(), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/email/templates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].templateCode").value("WELCOME_EMAIL"));
    }

    @Test
    void shouldListTemplatesWithCategoryFilter() throws Exception {
        Page<EmailTemplateResponseDTO> emptyPage = new PageImpl<>(java.util.Collections.emptyList(), PageRequest.of(0, 20), 0);
        when(emailTemplateService.listTemplates(eq("NOTIFICATION"), any(), any(Pageable.class)))
                .thenReturn(emptyPage);

        mockMvc.perform(get("/api/v1/email/templates")
                        .param("category", "NOTIFICATION"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateTemplate() throws Exception {
        EmailTemplateRequestDTO request = new EmailTemplateRequestDTO();
        request.setTemplateCode("WELCOME_EMAIL");
        request.setTemplateName("Updated Welcome");
        request.setSubject("Updated Subject");
        request.setCategory("NOTIFICATION");

        EmailTemplateResponseDTO response = new EmailTemplateResponseDTO();
        response.setId(UUID.randomUUID());
        response.setTemplateCode("WELCOME_EMAIL");
        response.setTemplateName("Updated Welcome");
        response.setSubject("Updated Subject");

        when(emailTemplateService.updateTemplate(eq("WELCOME_EMAIL"), eq("th"), any(EmailTemplateRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/email/templates/{templateCode}", "WELCOME_EMAIL")
                        .param("language", "th")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.templateName").value("Updated Welcome"))
                .andExpect(jsonPath("$.subject").value("Updated Subject"));
    }

    @Test
    void shouldDeleteTemplate() throws Exception {
        mockMvc.perform(delete("/api/v1/email/templates/{templateCode}", "WELCOME_EMAIL")
                        .param("language", "th"))
                .andExpect(status().isNoContent());
    }
}
