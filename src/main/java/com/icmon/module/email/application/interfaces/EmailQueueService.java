package com.icmon.module.email.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.email.presentation.dto.request.EmailSendRequestDTO;
import com.icmon.module.email.presentation.dto.response.EmailSendResponseDTO;

public interface EmailQueueService {

    void queueEmail(EmailSendRequestDTO request) throws SystemGlobalException;

    void processPendingEmails() throws SystemGlobalException;

    EmailSendResponseDTO getQueueStatus(String emailId) throws SystemGlobalException;

    long getPendingCount();

    long getFailedCount();
}
