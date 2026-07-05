package com.icmon.module.email.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.email.application.interfaces.EmailService;
import com.icmon.module.email.presentation.dto.request.BulkEmailRequestDTO;
import com.icmon.module.email.presentation.dto.response.EmailSendResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class SendBulkEmailUseCase {

    private final EmailService emailService;

    public SendBulkEmailUseCase(EmailService emailService) {
        this.emailService = emailService;
    }

    public EmailSendResponseDTO execute(BulkEmailRequestDTO request) throws SystemGlobalException {
        return emailService.sendBulkEmail(request);
    }
}
