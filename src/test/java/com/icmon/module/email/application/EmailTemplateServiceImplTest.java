package com.icmon.module.email.application;

import com.icmon.module.email.application.impl.EmailTemplateServiceImpl;
import com.icmon.module.email.domain.MEmailTemplate;
import com.icmon.module.email.infrastructure.cache.EmailTemplateCacheService;
import com.icmon.module.email.infrastructure.entity.EmailTemplateEntity;
import com.icmon.module.email.infrastructure.mapper.EmailTemplateMapper;
import com.icmon.module.email.infrastructure.repository.EmailTemplateRepository;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailTemplateServiceImplTest {

    @Mock
    private EmailTemplateRepository templateRepository;
    @Mock
    private EmailTemplateMapper templateMapper;
    @Mock
    private EmailTemplateCacheService templateCacheService;

    private EmailTemplateServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new EmailTemplateServiceImpl(templateRepository, templateMapper, templateCacheService);
    }

    @Test
    void shouldCreateTemplate() throws Exception {
        EmailTemplateRequestDTO request = new EmailTemplateRequestDTO();
        request.setTemplateCode("WELCOME_EMAIL");
        request.setTemplateName("Welcome Email");
        request.setSubject("Welcome {name}");
        request.setBodyText("Hello {name}, welcome!");
        request.setCategory("NOTIFICATION");
        request.setLanguage("th");

        when(templateRepository.existsByTemplateCode("WELCOME_EMAIL")).thenReturn(false);

        EmailTemplateEntity savedEntity = new EmailTemplateEntity();
        savedEntity.setId(UUID.randomUUID());
        savedEntity.setTemplateCode("WELCOME_EMAIL");
        savedEntity.setTemplateName("Welcome Email");
        savedEntity.setSubject("Welcome {name}");
        savedEntity.setCategory("NOTIFICATION");
        savedEntity.setLanguage("th");
        savedEntity.setVersion(1);
        savedEntity.setIsActive(true);

        when(templateRepository.save(any(EmailTemplateEntity.class))).thenReturn(savedEntity);
        when(templateMapper.toDomain(any(EmailTemplateEntity.class))).thenReturn(new MEmailTemplate());

        EmailTemplateResponseDTO response = service.createTemplate(request);

        assertNotNull(response);
        assertEquals("WELCOME_EMAIL", response.getTemplateCode());
        verify(templateRepository).save(any(EmailTemplateEntity.class));
        verify(templateCacheService).saveTemplate(any(MEmailTemplate.class));
    }

    @Test
    void shouldThrowWhenTemplateCodeExists() {
        EmailTemplateRequestDTO request = new EmailTemplateRequestDTO();
        request.setTemplateCode("EXISTING");

        when(templateRepository.existsByTemplateCode("EXISTING")).thenReturn(true);

        assertThrows(Exception.class, () -> service.createTemplate(request));
    }

    @Test
    void shouldGetTemplateFromCache() throws Exception {
        MEmailTemplate cached = new MEmailTemplate();
        cached.setId(UUID.randomUUID());
        cached.setTemplateCode("WELCOME_EMAIL");
        cached.setTemplateName("Welcome Email");
        cached.setSubject("Welcome {name}");
        cached.setCategory("NOTIFICATION");
        cached.setLanguage("th");

        when(templateCacheService.getTemplate("WELCOME_EMAIL", "th")).thenReturn(cached);

        EmailTemplateResponseDTO response = service.getTemplate("WELCOME_EMAIL", "th");

        assertNotNull(response);
        assertEquals("WELCOME_EMAIL", response.getTemplateCode());
        verify(templateRepository, never()).findByTemplateCodeAndLanguage(anyString(), anyString());
    }

    @Test
    void shouldGetTemplateFromDatabaseWhenNotInCache() throws Exception {
        when(templateCacheService.getTemplate("WELCOME_EMAIL", "th")).thenReturn(null);

        EmailTemplateEntity entity = new EmailTemplateEntity();
        entity.setId(UUID.randomUUID());
        entity.setTemplateCode("WELCOME_EMAIL");
        entity.setSubject("Welcome {name}");
        entity.setCategory("NOTIFICATION");
        entity.setLanguage("th");

        when(templateRepository.findByTemplateCodeAndLanguage("WELCOME_EMAIL", "th"))
                .thenReturn(Optional.of(entity));
        when(templateMapper.toDomain(entity)).thenReturn(new MEmailTemplate() {{
            setTemplateCode("WELCOME_EMAIL");
        }});

        EmailTemplateResponseDTO response = service.getTemplate("WELCOME_EMAIL", "th");

        assertNotNull(response);
        assertEquals("WELCOME_EMAIL", response.getTemplateCode());
        verify(templateCacheService).saveTemplate(any(MEmailTemplate.class));
    }

    @Test
    void shouldThrowWhenTemplateNotFound() {
        when(templateCacheService.getTemplate("INVALID", "th")).thenReturn(null);
        when(templateRepository.findByTemplateCodeAndLanguage("INVALID", "th"))
                .thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> service.getTemplate("INVALID", "th"));
    }

    @Test
    void shouldListAllTemplates() throws Exception {
        EmailTemplateEntity entity = new EmailTemplateEntity();
        entity.setId(UUID.randomUUID());
        entity.setTemplateCode("WELCOME_EMAIL");
        entity.setSubject("Welcome");

        Page<EmailTemplateEntity> page = new PageImpl<>(List.of(entity), PageRequest.of(0, 20), 1);
        when(templateRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<EmailTemplateResponseDTO> result = service.listTemplates(null, null, PageRequest.of(0, 20));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("WELCOME_EMAIL", result.getContent().get(0).getTemplateCode());
    }

    @Test
    void shouldFilterByCategory() throws Exception {
        when(templateRepository.findByCategory(eq("NOTIFICATION"), any(Pageable.class)))
                .thenReturn(Page.empty());

        Page<EmailTemplateResponseDTO> result = service.listTemplates("NOTIFICATION", null, PageRequest.of(0, 20));

        assertNotNull(result);
        verify(templateRepository).findByCategory(eq("NOTIFICATION"), any(Pageable.class));
    }

    @Test
    void shouldDeleteTemplate() throws Exception {
        EmailTemplateEntity entity = new EmailTemplateEntity();
        entity.setId(UUID.randomUUID());
        entity.setTemplateCode("WELCOME_EMAIL");
        entity.setIsActive(true);

        when(templateRepository.findByTemplateCodeAndLanguage("WELCOME_EMAIL", "th"))
                .thenReturn(Optional.of(entity));

        service.deleteTemplate("WELCOME_EMAIL", "th");

        assertFalse(entity.getIsActive());
        verify(templateRepository).save(entity);
        verify(templateCacheService).evictTemplate("WELCOME_EMAIL", "th");
    }
}
