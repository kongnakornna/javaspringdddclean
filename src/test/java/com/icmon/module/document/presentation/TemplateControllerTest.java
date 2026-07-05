package com.icmon.module.document.presentation.controller;

import com.icmon.module.document.application.usecase.TemplateUseCase;
import com.icmon.module.document.presentation.dto.response.TemplateResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TemplateControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TemplateUseCase templateUseCase;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TemplateController(templateUseCase))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
    }

    @Test
    void shouldGetTemplateByCode() throws Exception {
        TemplateResponseDTO response = new TemplateResponseDTO();
        response.setId(UUID.randomUUID());
        response.setTemplateCode("TMPL-001");
        response.setTemplateName("Invoice Template");

        when(templateUseCase.getTemplate("TMPL-001")).thenReturn(response);

        mockMvc.perform(get("/api/v1/templates/{code}", "TMPL-001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.templateCode").value("TMPL-001"))
                .andExpect(jsonPath("$.templateName").value("Invoice Template"));
    }

    @Test
    void shouldListTemplates() throws Exception {
        TemplateResponseDTO response = new TemplateResponseDTO();
        response.setId(UUID.randomUUID());
        response.setTemplateCode("TMPL-001");

        Page<TemplateResponseDTO> page = new PageImpl<>(List.of(response), PageRequest.of(0, 20), 1);

        when(templateUseCase.listTemplates(any(), any())).thenReturn(page);

        mockMvc.perform(get("/api/v1/templates")
                        .param("templateType", "JASPER"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].templateCode").value("TMPL-001"));
    }

    @Test
    void shouldDeleteTemplate() throws Exception {
        mockMvc.perform(delete("/api/v1/templates/{code}", "TMPL-001"))
                .andExpect(status().isNoContent());
    }
}
