package com.icmon.module.email.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.email.application.interfaces.EmailService;
import com.icmon.module.email.presentation.dto.request.EmailSendRequestDTO;
import com.icmon.module.email.presentation.dto.response.EmailSendResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class SendEmailUseCase {

    private final EmailService emailService;

    public SendEmailUseCase(EmailService emailService) {
        this.emailService = emailService;
    }

    public EmailSendResponseDTO execute(EmailSendRequestDTO request) throws SystemGlobalException {
        return emailService.sendEmail(request);
    }
}
