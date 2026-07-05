package com.icmon.module.email.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import java.time.LocalDateTime;

public class TEmailQueue extends GenericBusinessClass {

    private String emailId;
    private String templateCode;
    private String referenceType;
    private java.util.UUID referenceId;
    private String fromEmail;
    private String toEmail;
    private String toName;
    private String subject;
    private String bodyHtml;
    private String bodyText;
    private String attachments;
    private String priority;
    private String status;
    private Integer retryCount;
    private Integer maxRetry;
    private LocalDateTime nextAttemptAt;
    private String errorMessage;

    public void markAsProcessing() {
        this.status = "PROCESSING";
    }

    public void markAsSent() {
        this.status = "SENT";
    }

    public void markAsFailed(String error) {
        this.status = "FAILED";
        this.errorMessage = error;
        this.retryCount = (this.retryCount != null ? this.retryCount : 0) + 1;
        if (this.retryCount < (this.maxRetry != null ? this.maxRetry : 3)) {
            this.nextAttemptAt = LocalDateTime.now().plusMinutes(5);
        }
    }

    public boolean canRetry() {
        return "FAILED".equals(this.status)
                && (this.retryCount == null || this.retryCount < (this.maxRetry != null ? this.maxRetry : 3));
    }

    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }
    public String getTemplateCode() { return templateCode; }
    public void setTemplateCode(String templateCode) { this.templateCode = templateCode; }
    public String getReferenceType() { return referenceType; }
    public void setReferenceType(String referenceType) { this.referenceType = referenceType; }
    public java.util.UUID getReferenceId() { return referenceId; }
    public void setReferenceId(java.util.UUID referenceId) { this.referenceId = referenceId; }
    public String getFromEmail() { return fromEmail; }
    public void setFromEmail(String fromEmail) { this.fromEmail = fromEmail; }
    public String getToEmail() { return toEmail; }
    public void setToEmail(String toEmail) { this.toEmail = toEmail; }
    public String getToName() { return toName; }
    public void setToName(String toName) { this.toName = toName; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getBodyHtml() { return bodyHtml; }
    public void setBodyHtml(String bodyHtml) { this.bodyHtml = bodyHtml; }
    public String getBodyText() { return bodyText; }
    public void setBodyText(String bodyText) { this.bodyText = bodyText; }
    public String getAttachments() { return attachments; }
    public void setAttachments(String attachments) { this.attachments = attachments; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getRetryCount() { return retryCount; }
    public void setRetryCount(Integer retryCount) { this.retryCount = retryCount; }
    public Integer getMaxRetry() { return maxRetry; }
    public void setMaxRetry(Integer maxRetry) { this.maxRetry = maxRetry; }
    public LocalDateTime getNextAttemptAt() { return nextAttemptAt; }
    public void setNextAttemptAt(LocalDateTime nextAttemptAt) { this.nextAttemptAt = nextAttemptAt; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
