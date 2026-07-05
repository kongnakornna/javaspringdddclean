package com.icmon.module.email.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.email.presentation.dto.response.EmailHistoryResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EmailHistoryService {

    EmailHistoryResponseDTO getHistoryById(UUID id) throws SystemGlobalException;

    EmailHistoryResponseDTO getHistoryByEmailId(String emailId) throws SystemGlobalException;

    Page<EmailHistoryResponseDTO> searchHistories(String status, String templateCode, String toEmail, Pageable pageable) throws SystemGlobalException;
}
