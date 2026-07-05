package com.icmon.module.email.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.email.application.interfaces.EmailTemplateService;
import com.icmon.module.email.presentation.dto.request.EmailTemplateRequestDTO;
import com.icmon.module.email.presentation.dto.response.EmailTemplateResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class CreateEmailTemplateUseCase {

    private final EmailTemplateService emailTemplateService;

    public CreateEmailTemplateUseCase(EmailTemplateService emailTemplateService) {
        this.emailTemplateService = emailTemplateService;
    }

    public EmailTemplateResponseDTO execute(EmailTemplateRequestDTO request) throws SystemGlobalException {
        return emailTemplateService.createTemplate(request);
    }
}
