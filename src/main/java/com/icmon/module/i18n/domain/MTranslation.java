package com.icmon.module.i18n.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import java.time.LocalDateTime;
import java.util.UUID;

public class MTranslation extends GenericBusinessClass {

    private String messageKey;
    private String languageCode;
    private String messageText;
    private String context;
    private String description;
    private Integer version;
    private Boolean isApproved;
    private UUID approvedBy;
    private LocalDateTime approvedAt;

    public void approve(UUID approverId) {
        this.isApproved = true;
        this.approvedBy = approverId;
        this.approvedAt = LocalDateTime.now();
    }

    public boolean isApproved() {
        return Boolean.TRUE.equals(isApproved);
    }

    public String getMessageKey() { return messageKey; }
    public void setMessageKey(String messageKey) { this.messageKey = messageKey; }
    public String getLanguageCode() { return languageCode; }
    public void setLanguageCode(String languageCode) { this.languageCode = languageCode; }
    public String getMessageText() { return messageText; }
    public void setMessageText(String messageText) { this.messageText = messageText; }
    public String getContext() { return context; }
    public void setContext(String context) { this.context = context; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
    public Boolean getIsApproved() { return isApproved; }
    public void setApproved(Boolean approved) { isApproved = approved; }
    public UUID getApprovedBy() { return approvedBy; }
    public void setApprovedBy(UUID approvedBy) { this.approvedBy = approvedBy; }
    public LocalDateTime getApprovedAt() { return approvedAt; }
    public void setApprovedAt(LocalDateTime approvedAt) { this.approvedAt = approvedAt; }
}
