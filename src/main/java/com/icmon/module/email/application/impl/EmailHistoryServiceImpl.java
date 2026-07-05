package com.icmon.module.email.application.impl;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.email.application.interfaces.EmailHistoryService;
import com.icmon.module.email.infrastructure.entity.EmailHistoryEntity;
import com.icmon.module.email.infrastructure.mapper.EmailHistoryMapper;
import com.icmon.module.email.infrastructure.repository.EmailHistoryRepository;
import com.icmon.module.email.presentation.dto.response.EmailHistoryResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class EmailHistoryServiceImpl implements EmailHistoryService {

    private static final Logger log = LoggerFactory.getLogger(EmailHistoryServiceImpl.class);

    private final EmailHistoryRepository historyRepository;
    private final EmailHistoryMapper historyMapper;

    public EmailHistoryServiceImpl(EmailHistoryRepository historyRepository,
                                   EmailHistoryMapper historyMapper) {
        this.historyRepository = historyRepository;
        this.historyMapper = historyMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public EmailHistoryResponseDTO getHistoryById(UUID id) throws SystemGlobalException {
        EmailHistoryEntity entity = historyRepository.findById(id)
                .orElseThrow(() -> new SystemGlobalException("Email history not found with id: " + id, null));
        return EmailHistoryResponseDTO.fromEntity(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public EmailHistoryResponseDTO getHistoryByEmailId(String emailId) throws SystemGlobalException {
        EmailHistoryEntity entity = historyRepository.findByEmailId(emailId)
                .orElseThrow(() -> new SystemGlobalException("Email history not found: " + emailId, null));
        return EmailHistoryResponseDTO.fromEntity(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmailHistoryResponseDTO> searchHistories(String status, String templateCode, String toEmail, Pageable pageable) throws SystemGlobalException {
        return historyRepository.searchHistories(status, templateCode, toEmail, pageable)
                .map(EmailHistoryResponseDTO::fromEntity);
    }
}
