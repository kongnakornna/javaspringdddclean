package com.icmon.module.document.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import java.time.LocalDateTime;
import java.util.UUID;

public class TDocumentHistory extends GenericBusinessClass {
    private UUID documentId;
    private String action;
    private UUID performedBy;
    private LocalDateTime performedAt;
    private String details;

    public UUID getDocumentId() { return documentId; }
    public void setDocumentId(UUID documentId) { this.documentId = documentId; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public UUID getPerformedBy() { return performedBy; }
    public void setPerformedBy(UUID performedBy) { this.performedBy = performedBy; }
    public LocalDateTime getPerformedAt() { return performedAt; }
    public void setPerformedAt(LocalDateTime performedAt) { this.performedAt = performedAt; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
}
