package com.icmon.module.document.application.service;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.document.application.interfaces.TemplateService;
import com.icmon.module.document.infrastructure.entity.DocumentTemplateEntity;
import com.icmon.module.document.infrastructure.repository.DocumentTemplateRepository;
import com.icmon.module.document.infrastructure.storage.FileStorageService;
import org.springframework.beans.factory.annotation.Qualifier;
import com.icmon.module.document.presentation.dto.request.TemplateUploadRequestDTO;
import com.icmon.module.document.presentation.dto.response.TemplateResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@Slf4j
@Transactional
public class TemplateServiceImpl implements TemplateService {

    private final DocumentTemplateRepository templateRepository;
    private final FileStorageService fileStorageService;

    public TemplateServiceImpl(DocumentTemplateRepository templateRepository,
                               @Qualifier("localFileStorageService") FileStorageService fileStorageService) {
        this.templateRepository = templateRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    @Transactional
    public TemplateResponseDTO uploadTemplate(MultipartFile file, TemplateUploadRequestDTO request) throws SystemGlobalException {
        log.info("Uploading template: code={}, name={}, type={}", request.getTemplateCode(), request.getTemplateName(), request.getTemplateType());

        String filePath = fileStorageService.storeMultipartFile(file, "templates/" + request.getTemplateType());

        DocumentTemplateEntity entity = new DocumentTemplateEntity();
        entity.setTemplateCode(request.getTemplateCode());
        entity.setTemplateName(request.getTemplateName());
        entity.setTemplateType(request.getTemplateType());
        entity.setFilePath(filePath);
        entity.setIsActive(true);
        entity.setUploadedAt(LocalDateTime.now());
        entity.setDescription(request.getDescription());

        entity = templateRepository.save(entity);

        return toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public TemplateResponseDTO getTemplate(String templateCode) throws SystemGlobalException {
        DocumentTemplateEntity entity = templateRepository.findByTemplateCode(templateCode)
                .orElseThrow(() -> new SystemGlobalException("Template not found: " + templateCode, null));
        return toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TemplateResponseDTO> listTemplates(String templateType, Pageable pageable) throws SystemGlobalException {
        Page<DocumentTemplateEntity> entities;
        if (templateType != null && !templateType.isEmpty()) {
            entities = templateRepository.findByTemplateType(templateType, pageable);
        } else {
            entities = templateRepository.findByIsActiveTrue(pageable);
        }
        return entities.map(this::toResponseDTO);
    }

    @Override
    @Transactional
    public void deleteTemplate(String templateCode) throws SystemGlobalException {
        DocumentTemplateEntity entity = templateRepository.findByTemplateCode(templateCode)
                .orElseThrow(() -> new SystemGlobalException("Template not found: " + templateCode, null));
        if (entity.getFilePath() != null) {
            fileStorageService.deleteFile(entity.getFilePath());
        }
        templateRepository.delete(entity);
    }

    private TemplateResponseDTO toResponseDTO(DocumentTemplateEntity entity) {
        TemplateResponseDTO dto = new TemplateResponseDTO();
        dto.setId(entity.getId());
        dto.setTemplateCode(entity.getTemplateCode());
        dto.setTemplateName(entity.getTemplateName());
        dto.setTemplateType(entity.getTemplateType());
        dto.setFilePath(entity.getFilePath());
        dto.setIsActive(entity.getIsActive());
        dto.setUploadedAt(entity.getUploadedAt());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}
