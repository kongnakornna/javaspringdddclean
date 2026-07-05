package com.icmon.module.email.domain.valueobjects;

public class EmailContent {

    private String subject;
    private String bodyHtml;
    private String bodyText;

    public EmailContent() {}

    public EmailContent(String subject, String bodyHtml, String bodyText) {
        this.subject = subject;
        this.bodyHtml = bodyHtml;
        this.bodyText = bodyText;
    }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getBodyHtml() { return bodyHtml; }
    public void setBodyHtml(String bodyHtml) { this.bodyHtml = bodyHtml; }
    public String getBodyText() { return bodyText; }
    public void setBodyText(String bodyText) { this.bodyText = bodyText; }
}
