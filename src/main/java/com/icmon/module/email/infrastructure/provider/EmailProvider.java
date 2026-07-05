package com.icmon.module.email.infrastructure.provider;

import java.util.Map;

public interface EmailProvider {

    boolean sendEmail(String from, String to, String subject, String bodyHtml, String bodyText, Map<String, String> attachments);
}
