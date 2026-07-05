package com.icmon.module.email.application.impl;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.email.application.interfaces.EmailTemplateService;
import com.icmon.module.email.domain.MEmailTemplate;
import com.icmon.module.email.infrastructure.cache.EmailTemplateCacheService;
import com.icmon.module.email.infrastructure.entity.EmailTemplateEntity;
import com.icmon.module.email.infrastructure.mapper.EmailTemplateMapper;
import com.icmon.module.email.infrastructure.repository.EmailTemplateRepository;
import com.icmon.module.email.presentation.dto.request.EmailTemplateRequestDTO;
import com.icmon.module.email.presentation.dto.response.EmailTemplateResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private static final Logger log = LoggerFactory.getLogger(EmailTemplateServiceImpl.class);

    private final EmailTemplateRepository templateRepository;
    private final EmailTemplateMapper templateMapper;
    private final EmailTemplateCacheService templateCacheService;

    public EmailTemplateServiceImpl(EmailTemplateRepository templateRepository,
                                    EmailTemplateMapper templateMapper,
                                    EmailTemplateCacheService templateCacheService) {
        this.templateRepository = templateRepository;
        this.templateMapper = templateMapper;
        this.templateCacheService = templateCacheService;
    }

    @Override
    public EmailTemplateResponseDTO createTemplate(EmailTemplateRequestDTO request) throws SystemGlobalException {
        log.info("Creating email template: {}", request.getTemplateCode());

        if (templateRepository.existsByTemplateCode(request.getTemplateCode())) {
            throw new SystemGlobalException("Template already exists with code: " + request.getTemplateCode(), null);
        }

        EmailTemplateEntity entity = new EmailTemplateEntity();
        entity.setTemplateCode(request.getTemplateCode());
        entity.setTemplateName(request.getTemplateName());
        entity.setSubject(request.getSubject());
        entity.setBodyHtml(request.getBodyHtml());
        entity.setBodyText(request.getBodyText());
        entity.setFromEmail(request.getFromEmail());
        entity.setFromName(request.getFromName());
        entity.setCategory(request.getCategory());
        entity.setLanguage(request.getLanguage() != null ? request.getLanguage() : "th");
        entity.setVersion(1);
        entity.setIsActive(true);
        entity.setIsDefault(false);
        entity.setVariables(request.getVariables());
        entity.setDescription(request.getDescription());

        entity = templateRepository.save(entity);

        MEmailTemplate domain = templateMapper.toDomain(entity);
        templateCacheService.saveTemplate(domain);

        return EmailTemplateResponseDTO.fromEntity(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public EmailTemplateResponseDTO getTemplate(String templateCode, String language) throws SystemGlobalException {
        MEmailTemplate cached = templateCacheService.getTemplate(templateCode, language);
        if (cached != null) {
            EmailTemplateResponseDTO dto = new EmailTemplateResponseDTO();
            dto.setId(cached.getId());
            dto.setTemplateCode(cached.getTemplateCode());
            dto.setTemplateName(cached.getTemplateName());
            dto.setSubject(cached.getSubject());
            dto.setBodyHtml(cached.getBodyHtml());
            dto.setBodyText(cached.getBodyText());
            dto.setFromEmail(cached.getFromEmail());
            dto.setFromName(cached.getFromName());
            dto.setCategory(cached.getCategory());
            dto.setLanguage(cached.getLanguage());
            dto.setVersion(cached.getVersion());
            dto.setIsActive(cached.getIsActive());
            dto.setIsDefault(cached.getIsDefault());
            dto.setVariables(cached.getVariables());
            dto.setDescription(cached.getDescription());
            return dto;
        }

        EmailTemplateEntity entity = templateRepository.findByTemplateCodeAndLanguage(templateCode, language)
                .orElseThrow(() -> new SystemGlobalException("Email template not found: " + templateCode, null));

        MEmailTemplate domain = templateMapper.toDomain(entity);
        templateCacheService.saveTemplate(domain);

        return EmailTemplateResponseDTO.fromEntity(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmailTemplateResponseDTO> listTemplates(String category, String language, Pageable pageable) throws SystemGlobalException {
        Page<EmailTemplateEntity> entities;
        if (category != null && language != null) {
            entities = templateRepository.findByCategoryAndLanguage(category, language, pageable);
        } else if (category != null) {
            entities = templateRepository.findByCategory(category, pageable);
        } else if (language != null) {
            entities = templateRepository.findByLanguage(language, pageable);
        } else {
            entities = templateRepository.findAll(pageable);
        }
        return entities.map(EmailTemplateResponseDTO::fromEntity);
    }

    @Override
    public EmailTemplateResponseDTO updateTemplate(String templateCode, String language, EmailTemplateRequestDTO request) throws SystemGlobalException {
        log.info("Updating email template: {}", templateCode);

        EmailTemplateEntity entity = templateRepository.findByTemplateCodeAndLanguage(templateCode, language)
                .orElseThrow(() -> new SystemGlobalException("Email template not found: " + templateCode, null));

        if (request.getTemplateName() != null) entity.setTemplateName(request.getTemplateName());
        if (request.getSubject() != null) entity.setSubject(request.getSubject());
        if (request.getBodyHtml() != null) entity.setBodyHtml(request.getBodyHtml());
        if (request.getBodyText() != null) entity.setBodyText(request.getBodyText());
        if (request.getFromEmail() != null) entity.setFromEmail(request.getFromEmail());
        if (request.getFromName() != null) entity.setFromName(request.getFromName());
        if (request.getCategory() != null) entity.setCategory(request.getCategory());
        if (request.getVariables() != null) entity.setVariables(request.getVariables());
        if (request.getDescription() != null) entity.setDescription(request.getDescription());
        entity.setVersion(entity.getVersion() != null ? entity.getVersion() + 1 : 1);

        entity = templateRepository.save(entity);

        MEmailTemplate domain = templateMapper.toDomain(entity);
        templateCacheService.saveTemplate(domain);

        return EmailTemplateResponseDTO.fromEntity(entity);
    }

    @Override
    public void deleteTemplate(String templateCode, String language) throws SystemGlobalException {
        log.info("Deleting email template: {}", templateCode);

        EmailTemplateEntity entity = templateRepository.findByTemplateCodeAndLanguage(templateCode, language)
                .orElseThrow(() -> new SystemGlobalException("Email template not found: " + templateCode, null));

        entity.setIsActive(false);
        templateRepository.save(entity);

        templateCacheService.evictTemplate(templateCode, language);
    }
}
