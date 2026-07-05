package com.icmon.module.email.application.impl;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.email.application.interfaces.EmailQueueService;
import com.icmon.module.email.infrastructure.entity.EmailQueueEntity;
import com.icmon.module.email.infrastructure.provider.EmailProvider;
import com.icmon.module.email.infrastructure.repository.EmailHistoryRepository;
import com.icmon.module.email.infrastructure.repository.EmailQueueRepository;
import com.icmon.module.email.presentation.dto.request.EmailSendRequestDTO;
import com.icmon.module.email.presentation.dto.response.EmailSendResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class EmailQueueServiceImpl implements EmailQueueService {

    private static final Logger log = LoggerFactory.getLogger(EmailQueueServiceImpl.class);

    private final EmailQueueRepository queueRepository;
    private final EmailHistoryRepository historyRepository;
    private final EmailProvider emailProvider;

    public EmailQueueServiceImpl(EmailQueueRepository queueRepository,
                                 EmailHistoryRepository historyRepository,
                                 @Qualifier("smtpEmailProvider") EmailProvider emailProvider) {
        this.queueRepository = queueRepository;
        this.historyRepository = historyRepository;
        this.emailProvider = emailProvider;
    }

    @Override
    public void queueEmail(EmailSendRequestDTO request) throws SystemGlobalException {
        EmailQueueEntity entity = new EmailQueueEntity();
        entity.setFromEmail(request.getFromEmail() != null ? request.getFromEmail() : "noreply@icmon.com");
        entity.setToEmail(request.getToEmail());
        entity.setToName(request.getToName());
        entity.setSubject(request.getSubject());
        entity.setBodyHtml(request.getBodyHtml());
        entity.setBodyText(request.getBodyText());
        entity.setTemplateCode(request.getTemplateCode());
        entity.setStatus("PENDING");
        entity.setPriority(request.getPriority() != null ? request.getPriority() : "NORMAL");
        entity.setRetryCount(0);
        entity.setMaxRetry(3);
        entity.setNextAttemptAt(LocalDateTime.now());

        queueRepository.save(entity);
        log.info("Email queued for: {}", request.getToEmail());
    }

    @Override
    public void processPendingEmails() throws SystemGlobalException {
        List<EmailQueueEntity> pendingEmails = queueRepository.findPendingQueue(LocalDateTime.now());

        for (EmailQueueEntity queueEntity : pendingEmails) {
            try {
                queueEntity.setStatus("PROCESSING");
                queueRepository.save(queueEntity);

                boolean success = emailProvider.sendEmail(
                        queueEntity.getFromEmail(),
                        queueEntity.getToEmail(),
                        queueEntity.getSubject(),
                        queueEntity.getBodyHtml(),
                        queueEntity.getBodyText(),
                        null
                );

                if (success) {
                    queueEntity.setStatus("SENT");
                    historyRepository.findByEmailId(queueEntity.getEmailId()).ifPresent(history -> {
                        history.setStatus("SENT");
                        history.setSentAt(LocalDateTime.now());
                        historyRepository.save(history);
                    });
                } else {
                    markQueueFailed(queueEntity, "Provider returned failure");
                }
            } catch (Exception e) {
                log.error("Error processing queue item {}: {}", queueEntity.getId(), e.getMessage());
                markQueueFailed(queueEntity, e.getMessage());
            }
            queueRepository.save(queueEntity);
        }

        List<EmailQueueEntity> retryableEmails = queueRepository.findRetryableQueue(LocalDateTime.now());
        for (EmailQueueEntity queueEntity : retryableEmails) {
            try {
                queueEntity.setRetryCount(queueEntity.getRetryCount() != null ? queueEntity.getRetryCount() + 1 : 1);
                queueEntity.setStatus("PROCESSING");
                queueRepository.save(queueEntity);

                boolean success = emailProvider.sendEmail(
                        queueEntity.getFromEmail(),
                        queueEntity.getToEmail(),
                        queueEntity.getSubject(),
                        queueEntity.getBodyHtml(),
                        queueEntity.getBodyText(),
                        null
                );

                if (success) {
                    queueEntity.setStatus("SENT");
                } else {
                    queueEntity.setStatus("FAILED");
                    queueEntity.setErrorMessage("Provider returned failure on retry");
                }
            } catch (Exception e) {
                queueEntity.setStatus("FAILED");
                queueEntity.setErrorMessage(e.getMessage());
            }
            queueRepository.save(queueEntity);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public EmailSendResponseDTO getQueueStatus(String emailId) throws SystemGlobalException {
        EmailQueueEntity entity = queueRepository.findByEmailId(emailId)
                .orElseThrow(() -> new SystemGlobalException("Email queue not found: " + emailId, null));

        EmailSendResponseDTO response = new EmailSendResponseDTO();
        response.setEmailId(entity.getEmailId());
        response.setStatus(entity.getStatus());
        response.setToEmail(entity.getToEmail());
        response.setSubject(entity.getSubject());
        response.setMessage(entity.getErrorMessage());
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public long getPendingCount() {
        return queueRepository.countByStatus("PENDING");
    }

    @Override
    @Transactional(readOnly = true)
    public long getFailedCount() {
        return queueRepository.countByStatus("FAILED");
    }

    private void markQueueFailed(EmailQueueEntity entity, String error) {
        entity.setStatus("FAILED");
        entity.setErrorMessage(error);
        entity.setRetryCount(entity.getRetryCount() != null ? entity.getRetryCount() + 1 : 1);
        if (entity.getRetryCount() < (entity.getMaxRetry() != null ? entity.getMaxRetry() : 3)) {
            entity.setNextAttemptAt(LocalDateTime.now().plusMinutes(5));
        }
    }
}
