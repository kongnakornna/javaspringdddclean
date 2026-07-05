package com.icmon.module.email.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.email.application.interfaces.EmailHistoryService;
import com.icmon.module.email.presentation.dto.response.EmailHistoryResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetEmailHistoryUseCase {

    private final EmailHistoryService emailHistoryService;

    public GetEmailHistoryUseCase(EmailHistoryService emailHistoryService) {
        this.emailHistoryService = emailHistoryService;
    }

    public EmailHistoryResponseDTO byId(UUID id) throws SystemGlobalException {
        return emailHistoryService.getHistoryById(id);
    }

    public EmailHistoryResponseDTO byEmailId(String emailId) throws SystemGlobalException {
        return emailHistoryService.getHistoryByEmailId(emailId);
    }

    public Page<EmailHistoryResponseDTO> search(String status, String templateCode, String toEmail, Pageable pageable) throws SystemGlobalException {
        return emailHistoryService.searchHistories(status, templateCode, toEmail, pageable);
    }
}
