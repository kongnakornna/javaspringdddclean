package com.icmon.module.email.application;

import com.icmon.module.email.application.impl.EmailServiceImpl;
import com.icmon.module.email.domain.MEmailTemplate;
import com.icmon.module.email.infrastructure.cache.EmailTemplateCacheService;
import com.icmon.module.email.infrastructure.entity.EmailHistoryEntity;
import com.icmon.module.email.infrastructure.entity.EmailQueueEntity;
import com.icmon.module.email.infrastructure.mapper.EmailHistoryMapper;
import com.icmon.module.email.infrastructure.mapper.EmailTemplateMapper;
import com.icmon.module.email.infrastructure.provider.EmailProvider;
import com.icmon.module.email.infrastructure.repository.EmailHistoryRepository;
import com.icmon.module.email.infrastructure.repository.EmailQueueRepository;
import com.icmon.module.email.infrastructure.repository.EmailTemplateRepository;
import com.icmon.module.email.presentation.dto.request.EmailSendRequestDTO;
import com.icmon.module.email.presentation.dto.response.EmailSendResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Mock
    private EmailHistoryRepository historyRepository;
    @Mock
    private EmailQueueRepository queueRepository;
    @Mock
    private EmailTemplateRepository templateRepository;
    @Mock
    private EmailTemplateCacheService templateCacheService;
    @Mock
    private EmailTemplateMapper templateMapper;
    @Mock
    private EmailHistoryMapper historyMapper;
    @Mock
    private EmailProvider emailProvider;

    private EmailServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new EmailServiceImpl(historyRepository, queueRepository, templateRepository,
                templateCacheService, templateMapper, historyMapper, emailProvider);
    }

    @Test
    void shouldSendEmail() throws Exception {
        EmailSendRequestDTO request = new EmailSendRequestDTO();
        request.setToEmail("test@example.com");
        request.setSubject("Test Subject");
        request.setBodyText("Hello World");

        EmailHistoryEntity savedEntity = new EmailHistoryEntity();
        savedEntity.setId(UUID.randomUUID());
        savedEntity.setEmailId("EMAIL-20260705-0001");
        savedEntity.setFromEmail("noreply@icmon.com");
        savedEntity.setToEmail("test@example.com");
        savedEntity.setSubject("Test Subject");
        savedEntity.setStatus("PENDING");

        when(historyRepository.save(any(EmailHistoryEntity.class))).thenReturn(savedEntity);
        when(queueRepository.save(any(EmailQueueEntity.class))).thenReturn(new EmailQueueEntity());
        when(emailProvider.sendEmail(any(), any(), any(), any(), any(), any()))
                .thenReturn(true);

        EmailSendResponseDTO response = service.sendEmail(request);

        assertNotNull(response);
        assertEquals("test@example.com", response.getToEmail());
        assertEquals("Test Subject", response.getSubject());
        verify(historyRepository, atLeastOnce()).save(any(EmailHistoryEntity.class));
    }

    @Test
    void shouldSendTemplateEmail() throws Exception {
        MEmailTemplate template = new MEmailTemplate();
        template.setTemplateCode("QUOTATION_EMAIL");
        template.setSubject("ใบเสนอราคา #{quotationNo} - {customerName}");
        template.setBodyText("เรียน {customerName}");
        template.setFromEmail("noreply@icmon.com");
        template.setFromName("ICMON");

        when(templateCacheService.getTemplate("QUOTATION_EMAIL", "th")).thenReturn(template);

        EmailSendRequestDTO request = new EmailSendRequestDTO();
        request.setTemplateCode("QUOTATION_EMAIL");
        request.setToEmail("customer@example.com");
        request.setVariables(Map.of("quotationNo", "Q-001", "customerName", "สมชาย"));

        EmailHistoryEntity savedEntity = new EmailHistoryEntity();
        savedEntity.setId(UUID.randomUUID());
        savedEntity.setEmailId("EMAIL-20260705-0001");
        savedEntity.setToEmail("customer@example.com");
        savedEntity.setStatus("PENDING");

        when(historyRepository.save(any(EmailHistoryEntity.class))).thenReturn(savedEntity);
        when(queueRepository.save(any(EmailQueueEntity.class))).thenReturn(new EmailQueueEntity());
        when(emailProvider.sendEmail(any(), any(), any(), any(), any(), any()))
                .thenReturn(true);

        EmailSendResponseDTO response = service.sendTemplateEmail(request);

        assertNotNull(response);
        assertEquals("customer@example.com", response.getToEmail());
        verify(templateCacheService).getTemplate("QUOTATION_EMAIL", "th");
    }

    @Test
    void shouldThrowWhenTemplateNotFound() {
        when(templateCacheService.getTemplate("INVALID", "th")).thenReturn(null);
        when(templateRepository.findByTemplateCodeAndLanguageAndIsActiveTrue("INVALID", "th"))
                .thenReturn(Optional.empty());

        EmailSendRequestDTO request = new EmailSendRequestDTO();
        request.setTemplateCode("INVALID");
        request.setToEmail("test@example.com");

        assertThrows(Exception.class, () -> service.sendTemplateEmail(request));
    }

    @Test
    void shouldGetEmailStatus() throws Exception {
        String emailId = "EMAIL-20260705-0001";
        EmailHistoryEntity entity = new EmailHistoryEntity();
        entity.setId(UUID.randomUUID());
        entity.setEmailId(emailId);
        entity.setToEmail("test@example.com");
        entity.setSubject("Test");
        entity.setStatus("SENT");

        when(historyRepository.findByEmailId(emailId)).thenReturn(Optional.of(entity));

        EmailSendResponseDTO response = service.getEmailStatus(emailId);

        assertNotNull(response);
        assertEquals("SENT", response.getStatus());
        assertEquals(emailId, response.getEmailId());
    }

    @Test
    void shouldThrowWhenEmailNotFound() {
        when(historyRepository.findByEmailId("INVALID")).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> service.getEmailStatus("INVALID"));
    }
}
