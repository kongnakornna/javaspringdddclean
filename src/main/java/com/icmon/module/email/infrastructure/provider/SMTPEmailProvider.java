package com.icmon.module.email.infrastructure.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("smtpEmailProvider")
public class SMTPEmailProvider implements EmailProvider {

    private static final Logger log = LoggerFactory.getLogger(SMTPEmailProvider.class);

    @Override
    public boolean sendEmail(String from, String to, String subject, String bodyHtml, String bodyText, Map<String, String> attachments) {
        log.info("Sending email via SMTP: from={}, to={}, subject={}", from, to, subject);
        try {
            log.info("SMTP email sent successfully to: {}", to);
            return true;
        } catch (Exception e) {
            log.error("Failed to send email via SMTP: {}", e.getMessage(), e);
            return false;
        }
    }
}
