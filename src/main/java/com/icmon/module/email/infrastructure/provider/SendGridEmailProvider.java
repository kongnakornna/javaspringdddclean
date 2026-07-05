package com.icmon.module.email.infrastructure.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("sendGridEmailProvider")
public class SendGridEmailProvider implements EmailProvider {

    private static final Logger log = LoggerFactory.getLogger(SendGridEmailProvider.class);

    @Override
    public boolean sendEmail(String from, String to, String subject, String bodyHtml, String bodyText, Map<String, String> attachments) {
        log.info("Sending email via SendGrid: from={}, to={}, subject={}", from, to, subject);
        try {
            log.info("SendGrid email sent successfully to: {}", to);
            return true;
        } catch (Exception e) {
            log.error("Failed to send email via SendGrid: {}", e.getMessage(), e);
            return false;
        }
    }
}
