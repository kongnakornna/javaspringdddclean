package com.icmon.module.email.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.email.application.interfaces.EmailQueueService;
import com.icmon.module.email.presentation.dto.request.EmailSendRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class QueueEmailUseCase {

    private final EmailQueueService emailQueueService;

    public QueueEmailUseCase(EmailQueueService emailQueueService) {
        this.emailQueueService = emailQueueService;
    }

    public void execute(EmailSendRequestDTO request) throws SystemGlobalException {
        emailQueueService.queueEmail(request);
    }
}
