package com.icmon.module.email.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.email.application.interfaces.EmailService;
import com.icmon.module.email.presentation.dto.request.EmailSendRequestDTO;
import com.icmon.module.email.presentation.dto.response.EmailSendResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class SendEmailWithAttachmentUseCase {

    private final EmailService emailService;

    public SendEmailWithAttachmentUseCase(EmailService emailService) {
        this.emailService = emailService;
    }

    public EmailSendResponseDTO execute(EmailSendRequestDTO request, MultipartFile file) throws SystemGlobalException {
        Map<String, String> attachments = new HashMap<>();
        if (file != null && !file.isEmpty()) {
            attachments.put(file.getOriginalFilename(), file.getOriginalFilename());
        }
        request.setAttachments(attachments);
        return emailService.sendEmail(request);
    }
}
