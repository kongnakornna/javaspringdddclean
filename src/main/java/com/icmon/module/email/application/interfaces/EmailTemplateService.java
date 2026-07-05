package com.icmon.module.email.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.email.presentation.dto.request.EmailTemplateRequestDTO;
import com.icmon.module.email.presentation.dto.response.EmailTemplateResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmailTemplateService {

    EmailTemplateResponseDTO createTemplate(EmailTemplateRequestDTO request) throws SystemGlobalException;

    EmailTemplateResponseDTO getTemplate(String templateCode, String language) throws SystemGlobalException;

    Page<EmailTemplateResponseDTO> listTemplates(String category, String language, Pageable pageable) throws SystemGlobalException;

    EmailTemplateResponseDTO updateTemplate(String templateCode, String language, EmailTemplateRequestDTO request) throws SystemGlobalException;

    void deleteTemplate(String templateCode, String language) throws SystemGlobalException;
}
