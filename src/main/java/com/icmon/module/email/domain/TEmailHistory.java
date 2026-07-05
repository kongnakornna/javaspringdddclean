package com.icmon.module.email.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.email.domain.enums.EmailStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public class TEmailHistory extends GenericBusinessClass {

    private String emailId;
    private String templateCode;
    private String referenceType;
    private UUID referenceId;
    private String fromEmail;
    private String fromName;
    private String toEmail;
    private String toName;
    private String ccEmail;
    private String bccEmail;
    private String subject;
    private String bodyPreview;
    private EmailStatus status;
    private String priority;
    private LocalDateTime sentAt;
    private String errorMessage;
    private Integer retryCount;
    private String attachments;

    public void markAsSent() {
        this.status = EmailStatus.SENT;
        this.sentAt = LocalDateTime.now();
    }

    public void markAsFailed(String error) {
        this.status = EmailStatus.FAILED;
        this.errorMessage = error;
        this.retryCount = (this.retryCount != null ? this.retryCount : 0) + 1;
    }

    public void markAsBounced(String error) {
        this.status = EmailStatus.BOUNCED;
        this.errorMessage = error;
    }

    public boolean canResend() {
        return (this.status == EmailStatus.FAILED || this.status == EmailStatus.BOUNCED)
                && (this.retryCount == null || this.retryCount < 3);
    }

    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }
    public String getTemplateCode() { return templateCode; }
    public void setTemplateCode(String templateCode) { this.templateCode = templateCode; }
    public String getReferenceType() { return referenceType; }
    public void setReferenceType(String referenceType) { this.referenceType = referenceType; }
    public UUID getReferenceId() { return referenceId; }
    public void setReferenceId(UUID referenceId) { this.referenceId = referenceId; }
    public String getFromEmail() { return fromEmail; }
    public void setFromEmail(String fromEmail) { this.fromEmail = fromEmail; }
    public String getFromName() { return fromName; }
    public void setFromName(String fromName) { this.fromName = fromName; }
    public String getToEmail() { return toEmail; }
    public void setToEmail(String toEmail) { this.toEmail = toEmail; }
    public String getToName() { return toName; }
    public void setToName(String toName) { this.toName = toName; }
    public String getCcEmail() { return ccEmail; }
    public void setCcEmail(String ccEmail) { this.ccEmail = ccEmail; }
    public String getBccEmail() { return bccEmail; }
    public void setBccEmail(String bccEmail) { this.bccEmail = bccEmail; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getBodyPreview() { return bodyPreview; }
    public void setBodyPreview(String bodyPreview) { this.bodyPreview = bodyPreview; }
    public EmailStatus getStatus() { return status; }
    public void setStatus(EmailStatus status) { this.status = status; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public Integer getRetryCount() { return retryCount; }
    public void setRetryCount(Integer retryCount) { this.retryCount = retryCount; }
    public String getAttachments() { return attachments; }
    public void setAttachments(String attachments) { this.attachments = attachments; }
}
