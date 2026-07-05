package com.icmon.module.email.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.email.application.interfaces.EmailQueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ProcessEmailQueueUseCase {

    private static final Logger log = LoggerFactory.getLogger(ProcessEmailQueueUseCase.class);

    private final EmailQueueService emailQueueService;

    public ProcessEmailQueueUseCase(EmailQueueService emailQueueService) {
        this.emailQueueService = emailQueueService;
    }

    @Scheduled(fixedRate = 60000)
    public void processQueue() throws SystemGlobalException {
        log.info("Processing email queue...");
        emailQueueService.processPendingEmails();
        log.info("Email queue processing completed. Pending: {}, Failed: {}",
                emailQueueService.getPendingCount(), emailQueueService.getFailedCount());
    }
}
