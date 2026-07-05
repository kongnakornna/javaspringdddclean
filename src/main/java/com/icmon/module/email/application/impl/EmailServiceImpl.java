package com.icmon.module.email.application.impl;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.email.application.interfaces.EmailService;
import com.icmon.module.email.domain.MEmailTemplate;
import com.icmon.module.email.domain.TEmailHistory;
import com.icmon.module.email.domain.TEmailQueue;
import com.icmon.module.email.domain.enums.EmailStatus;
import com.icmon.module.email.infrastructure.cache.EmailTemplateCacheService;
import com.icmon.module.email.infrastructure.entity.EmailHistoryEntity;
import com.icmon.module.email.infrastructure.entity.EmailQueueEntity;
import com.icmon.module.email.infrastructure.entity.EmailTemplateEntity;
import com.icmon.module.email.infrastructure.mapper.EmailHistoryMapper;
import com.icmon.module.email.infrastructure.mapper.EmailTemplateMapper;
import com.icmon.module.email.infrastructure.provider.EmailProvider;
import com.icmon.module.email.infrastructure.repository.EmailHistoryRepository;
import com.icmon.module.email.infrastructure.repository.EmailQueueRepository;
import com.icmon.module.email.infrastructure.repository.EmailTemplateRepository;
import com.icmon.module.email.presentation.dto.request.BulkEmailRequestDTO;
import com.icmon.module.email.presentation.dto.request.EmailSendRequestDTO;
import com.icmon.module.email.presentation.dto.response.EmailSendResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final EmailHistoryRepository historyRepository;
    private final EmailQueueRepository queueRepository;
    private final EmailTemplateRepository templateRepository;
    private final EmailTemplateCacheService templateCacheService;
    private final EmailTemplateMapper templateMapper;
    private final EmailHistoryMapper historyMapper;
    private final EmailProvider emailProvider;

    public EmailServiceImpl(EmailHistoryRepository historyRepository,
                            EmailQueueRepository queueRepository,
                            EmailTemplateRepository templateRepository,
                            EmailTemplateCacheService templateCacheService,
                            EmailTemplateMapper templateMapper,
                            EmailHistoryMapper historyMapper,
                            @Qualifier("smtpEmailProvider") EmailProvider emailProvider) {
        this.historyRepository = historyRepository;
        this.queueRepository = queueRepository;
        this.templateRepository = templateRepository;
        this.templateCacheService = templateCacheService;
        this.templateMapper = templateMapper;
        this.historyMapper = historyMapper;
        this.emailProvider = emailProvider;
    }

    @Override
    public EmailSendResponseDTO sendEmail(EmailSendRequestDTO request) throws SystemGlobalException {
        log.info("Sending email to: {}", request.getToEmail());

        String subject = request.getSubject() != null ? request.getSubject() : "No Subject";
        String bodyPreview = request.getBodyText() != null ? request.getBodyText().substring(0, Math.min(request.getBodyText().length(), 200)) : "";

        EmailHistoryEntity history = new EmailHistoryEntity();
        history.setFromEmail(request.getFromEmail() != null ? request.getFromEmail() : "noreply@icmon.com");
        history.setFromName(request.getFromName());
        history.setToEmail(request.getToEmail());
        history.setToName(request.getToName());
        history.setCcEmail(request.getCcEmail());
        history.setBccEmail(request.getBccEmail());
        history.setSubject(subject);
        history.setBodyPreview(bodyPreview);
        history.setStatus(EmailStatus.PENDING.name());
        history.setPriority(request.getPriority() != null ? request.getPriority() : "NORMAL");
        history.setReferenceType(request.getReferenceType());
        history.setReferenceId(request.getReferenceId());

        history = historyRepository.save(history);

        queueAndSendAsync(history, null, subject, request.getBodyHtml(), request.getBodyText(), request.getAttachments());

        return EmailSendResponseDTO.fromEntity(history);
    }

    @Override
    public EmailSendResponseDTO sendTemplateEmail(EmailSendRequestDTO request) throws SystemGlobalException {
        log.info("Sending template email: {} to: {}", request.getTemplateCode(), request.getToEmail());

        MEmailTemplate template = templateCacheService.getTemplate(request.getTemplateCode(), request.getLanguage() != null ? request.getLanguage() : "th");
        if (template == null) {
            EmailTemplateEntity templateEntity = templateRepository.findByTemplateCodeAndLanguageAndIsActiveTrue(
                    request.getTemplateCode(), request.getLanguage() != null ? request.getLanguage() : "th")
                    .orElseThrow(() -> new SystemGlobalException("Email template not found: " + request.getTemplateCode(), null));
            template = templateMapper.toDomain(templateEntity);
            templateCacheService.saveTemplate(template);
        }

        Map<String, String> variables = request.getVariables();
        if (variables == null) {
            variables = Map.of();
        }

        String subject = template.renderSubject(variables);
        String bodyHtml = template.renderBodyHtml(variables);
        String bodyText = template.renderBodyText(variables);
        String bodyPreview = bodyText != null ? bodyText.substring(0, Math.min(bodyText.length(), 200)) : "";

        EmailHistoryEntity history = new EmailHistoryEntity();
        history.setTemplateCode(request.getTemplateCode());
        history.setReferenceType(request.getReferenceType() != null ? request.getReferenceType() : template.getCategory());
        history.setReferenceId(request.getReferenceId());
        history.setFromEmail(template.getFromEmail() != null ? template.getFromEmail() : "noreply@icmon.com");
        history.setFromName(template.getFromName());
        history.setToEmail(request.getToEmail());
        history.setToName(request.getToName());
        history.setCcEmail(request.getCcEmail());
        history.setBccEmail(request.getBccEmail());
        history.setSubject(subject);
        history.setBodyPreview(bodyPreview);
        history.setStatus(EmailStatus.PENDING.name());
        history.setPriority(request.getPriority() != null ? request.getPriority() : "NORMAL");

        history = historyRepository.save(history);

        queueAndSendAsync(history, template, subject, bodyHtml, bodyText, request.getAttachments());

        return EmailSendResponseDTO.fromEntity(history);
    }

    @Override
    public EmailSendResponseDTO sendBulkEmail(BulkEmailRequestDTO request) throws SystemGlobalException {
        log.info("Sending bulk email to {} recipients", request.getRecipients().size());

        for (BulkEmailRequestDTO.Recipient recipient : request.getRecipients()) {
            EmailSendRequestDTO singleRequest = new EmailSendRequestDTO();
            singleRequest.setTemplateCode(request.getTemplateCode());
            singleRequest.setToEmail(recipient.getEmail());
            singleRequest.setToName(recipient.getName());
            singleRequest.setVariables(recipient.getVariables());
            singleRequest.setLanguage(request.getLanguage());
            singleRequest.setPriority(request.getPriority());
            sendTemplateEmail(singleRequest);
        }

        EmailSendResponseDTO response = new EmailSendResponseDTO();
        response.setStatus("BULK_QUEUED");
        response.setMessage("Bulk email queued for " + request.getRecipients().size() + " recipients");
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public EmailSendResponseDTO getEmailStatus(String emailId) throws SystemGlobalException {
        EmailHistoryEntity entity = historyRepository.findByEmailId(emailId)
                .orElseThrow(() -> new SystemGlobalException("Email not found: " + emailId, null));
        return EmailSendResponseDTO.fromEntity(entity);
    }

    @Override
    public EmailSendResponseDTO resendEmail(String emailId) throws SystemGlobalException {
        EmailHistoryEntity entity = historyRepository.findByEmailId(emailId)
                .orElseThrow(() -> new SystemGlobalException("Email not found: " + emailId, null));

        TEmailHistory history = historyMapper.toDomain(entity);
        if (!history.canResend()) {
            throw new SystemGlobalException("Cannot resend email with status: " + history.getStatus(), null);
        }

        String templateCode = entity.getTemplateCode();
        MEmailTemplate template = null;
        if (templateCode != null) {
            template = templateCacheService.getTemplate(templateCode, "th");
            if (template == null) {
                EmailTemplateEntity templateEntity = templateRepository.findByTemplateCodeAndLanguage(templateCode, "th").orElse(null);
                if (templateEntity != null) {
                    template = templateMapper.toDomain(templateEntity);
                }
            }
        }

        queueAndSendAsync(entity, template, entity.getSubject(), null, null, null);

        return EmailSendResponseDTO.fromEntity(entity);
    }

    @Async
    protected void queueAndSendAsync(EmailHistoryEntity history, MEmailTemplate template,
                                     String subject, String bodyHtml, String bodyText,
                                     Map<String, String> attachments) {
        try {
            String fromEmail = history.getFromEmail();
            if (template != null && template.getFromEmail() != null) {
                fromEmail = template.getFromEmail();
            }

            EmailQueueEntity queueEntity = new EmailQueueEntity();
            queueEntity.setEmailId(history.getEmailId());
            queueEntity.setTemplateCode(history.getTemplateCode());
            queueEntity.setFromEmail(fromEmail);
            queueEntity.setToEmail(history.getToEmail());
            queueEntity.setToName(history.getToName());
            queueEntity.setSubject(subject);
            queueEntity.setBodyHtml(bodyHtml);
            queueEntity.setBodyText(bodyText);
            queueEntity.setStatus("PENDING");
            queueEntity.setPriority(history.getPriority());
            queueEntity.setNextAttemptAt(LocalDateTime.now());
            queueEntity.setWhitelabelId(history.getWhitelabelId() != null ? history.getWhitelabelId() : UUID.fromString("00000000-0000-0000-0000-000000000001"));
            queueRepository.save(queueEntity);

            boolean success = emailProvider.sendEmail(
                    fromEmail,
                    history.getToEmail(),
                    subject,
                    bodyHtml,
                    bodyText,
                    attachments
            );

            if (success) {
                history.setStatus(EmailStatus.SENT.name());
                history.setSentAt(LocalDateTime.now());
                queueEntity.setStatus("SENT");
            } else {
                history.setStatus(EmailStatus.FAILED.name());
                history.setErrorMessage("Provider returned failure");
                queueEntity.setStatus("FAILED");
                queueEntity.setErrorMessage("Provider returned failure");
            }

            historyRepository.save(history);
            queueRepository.save(queueEntity);

        } catch (Exception e) {
            log.error("Failed to send email: {}", e.getMessage(), e);
            history.setStatus(EmailStatus.FAILED.name());
            history.setErrorMessage(e.getMessage());
            historyRepository.save(history);
        }
    }
}
