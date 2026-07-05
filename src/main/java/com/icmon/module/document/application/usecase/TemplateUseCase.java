package com.icmon.module.document.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.document.application.interfaces.TemplateService;
import com.icmon.module.document.presentation.dto.request.TemplateUploadRequestDTO;
import com.icmon.module.document.presentation.dto.response.TemplateResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class TemplateUseCase {

    private final TemplateService templateService;

    public TemplateResponseDTO uploadTemplate(MultipartFile file, TemplateUploadRequestDTO request) throws SystemGlobalException {
        return templateService.uploadTemplate(file, request);
    }

    public TemplateResponseDTO getTemplate(String templateCode) throws SystemGlobalException {
        return templateService.getTemplate(templateCode);
    }

    public Page<TemplateResponseDTO> listTemplates(String templateType, Pageable pageable) throws SystemGlobalException {
        return templateService.listTemplates(templateType, pageable);
    }

    public void deleteTemplate(String templateCode) throws SystemGlobalException {
        templateService.deleteTemplate(templateCode);
    }
}
