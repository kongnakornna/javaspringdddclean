package com.icmon.module.email.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.email.presentation.dto.request.BulkEmailRequestDTO;
import com.icmon.module.email.presentation.dto.request.EmailSendRequestDTO;
import com.icmon.module.email.presentation.dto.response.EmailSendResponseDTO;

public interface EmailService {

    EmailSendResponseDTO sendEmail(EmailSendRequestDTO request) throws SystemGlobalException;

    EmailSendResponseDTO sendTemplateEmail(EmailSendRequestDTO request) throws SystemGlobalException;

    EmailSendResponseDTO sendBulkEmail(BulkEmailRequestDTO request) throws SystemGlobalException;

    EmailSendResponseDTO getEmailStatus(String emailId) throws SystemGlobalException;

    EmailSendResponseDTO resendEmail(String emailId) throws SystemGlobalException;
}
