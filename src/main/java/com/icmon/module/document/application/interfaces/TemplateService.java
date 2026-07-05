package com.icmon.module.document.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.document.presentation.dto.request.TemplateUploadRequestDTO;
import com.icmon.module.document.presentation.dto.response.TemplateResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface TemplateService {
    TemplateResponseDTO uploadTemplate(MultipartFile file, TemplateUploadRequestDTO request) throws SystemGlobalException;
    TemplateResponseDTO getTemplate(String templateCode) throws SystemGlobalException;
    Page<TemplateResponseDTO> listTemplates(String templateType, Pageable pageable) throws SystemGlobalException;
    void deleteTemplate(String templateCode) throws SystemGlobalException;
}
