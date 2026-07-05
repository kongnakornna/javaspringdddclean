package com.icmon.module.email.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icmon.module.email.application.interfaces.EmailService;
import com.icmon.module.email.presentation.controller.EmailController;
import com.icmon.module.email.presentation.dto.request.BulkEmailRequestDTO;
import com.icmon.module.email.presentation.dto.request.EmailSendRequestDTO;
import com.icmon.module.email.presentation.dto.response.EmailSendResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EmailControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmailService emailService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new EmailController(emailService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldSendEmail() throws Exception {
        EmailSendRequestDTO request = new EmailSendRequestDTO();
        request.setToEmail("test@example.com");
        request.setSubject("Test");
        request.setBodyText("Hello");

        EmailSendResponseDTO response = new EmailSendResponseDTO();
        response.setId(UUID.randomUUID());
        response.setToEmail("test@example.com");
        response.setSubject("Test");
        response.setStatus("PENDING");

        when(emailService.sendEmail(any(EmailSendRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/email/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.toEmail").value("test@example.com"))
                .andExpect(jsonPath("$.subject").value("Test"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void shouldSendTemplateEmail() throws Exception {
        EmailSendRequestDTO request = new EmailSendRequestDTO();
        request.setTemplateCode("QUOTATION_EMAIL");
        request.setToEmail("customer@example.com");
        request.setVariables(Map.of("name", "สมชาย"));

        EmailSendResponseDTO response = new EmailSendResponseDTO();
        response.setId(UUID.randomUUID());
        response.setToEmail("customer@example.com");
        response.setStatus("PENDING");

        when(emailService.sendTemplateEmail(any(EmailSendRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/email/send-template")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.toEmail").value("customer@example.com"));
    }

    @Test
    void shouldSendBulkEmail() throws Exception {
        BulkEmailRequestDTO request = new BulkEmailRequestDTO();
        request.setTemplateCode("NOTIFICATION");
        request.setLanguage("th");
        BulkEmailRequestDTO.Recipient recipient = new BulkEmailRequestDTO.Recipient();
        recipient.setEmail("user1@example.com");
        recipient.setName("User 1");
        request.setRecipients(List.of(recipient));

        EmailSendResponseDTO response = new EmailSendResponseDTO();
        response.setStatus("BULK_QUEUED");
        response.setMessage("Bulk email queued for 1 recipients");

        when(emailService.sendBulkEmail(any(BulkEmailRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/email/bulk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("BULK_QUEUED"))
                .andExpect(jsonPath("$.message").value("Bulk email queued for 1 recipients"));
    }

    @Test
    void shouldGetEmailStatus() throws Exception {
        String emailId = "EMAIL-20260705-0001";

        EmailSendResponseDTO response = new EmailSendResponseDTO();
        response.setEmailId(emailId);
        response.setStatus("SENT");
        response.setToEmail("test@example.com");

        when(emailService.getEmailStatus(emailId)).thenReturn(response);

        mockMvc.perform(get("/api/v1/email/status/{emailId}", emailId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emailId").value(emailId))
                .andExpect(jsonPath("$.status").value("SENT"));
    }

    @Test
    void shouldResendEmail() throws Exception {
        String emailId = "EMAIL-20260705-0001";

        EmailSendResponseDTO response = new EmailSendResponseDTO();
        response.setEmailId(emailId);
        response.setStatus("PENDING");

        when(emailService.resendEmail(emailId)).thenReturn(response);

        mockMvc.perform(post("/api/v1/email/resend/{emailId}", emailId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emailId").value(emailId))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }
}
