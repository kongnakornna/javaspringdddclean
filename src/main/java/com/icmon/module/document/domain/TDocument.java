package com.icmon.module.document.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.document.domain.enums.DocumentStatus;
import com.icmon.module.document.domain.enums.DocumentType;
import java.time.LocalDateTime;
import java.util.UUID;

public class TDocument extends GenericBusinessClass {
    private String documentNo;
    private DocumentType documentType;
    private String documentSubType;
    private String referenceType;
    private UUID referenceId;
    private UUID templateId;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private String mimeType;
    private DocumentStatus status;
    private UUID generatedBy;
    private LocalDateTime generatedAt;
    private LocalDateTime uploadedAt;
    private UUID sentBy;
    private LocalDateTime sentAt;
    private String sentToEmail;
    private String description;
    private String[] tags;
    private String metadata;

    public void markAsSent(String email) {
        if (this.status == DocumentStatus.DELETED) {
            throw new IllegalStateException("Cannot send a deleted document.");
        }
        this.status = DocumentStatus.SENT;
        this.sentAt = LocalDateTime.now();
        this.sentToEmail = email;
    }

    public void archive() {
        if (this.status == DocumentStatus.DELETED) {
            throw new IllegalStateException("Cannot archive a deleted document.");
        }
        this.status = DocumentStatus.ARCHIVED;
    }

    public boolean canDownload() {
        return this.status != DocumentStatus.DELETED && this.filePath != null;
    }

    public String getDocumentNo() { return documentNo; }
    public void setDocumentNo(String documentNo) { this.documentNo = documentNo; }
    public DocumentType getDocumentType() { return documentType; }
    public void setDocumentType(DocumentType documentType) { this.documentType = documentType; }
    public String getDocumentSubType() { return documentSubType; }
    public void setDocumentSubType(String documentSubType) { this.documentSubType = documentSubType; }
    public String getReferenceType() { return referenceType; }
    public void setReferenceType(String referenceType) { this.referenceType = referenceType; }
    public UUID getReferenceId() { return referenceId; }
    public void setReferenceId(UUID referenceId) { this.referenceId = referenceId; }
    public UUID getTemplateId() { return templateId; }
    public void setTemplateId(UUID templateId) { this.templateId = templateId; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }
    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }
    public DocumentStatus getStatus() { return status; }
    public void setStatus(DocumentStatus status) { this.status = status; }
    public UUID getGeneratedBy() { return generatedBy; }
    public void setGeneratedBy(UUID generatedBy) { this.generatedBy = generatedBy; }
    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
    public UUID getSentBy() { return sentBy; }
    public void setSentBy(UUID sentBy) { this.sentBy = sentBy; }
    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
    public String getSentToEmail() { return sentToEmail; }
    public void setSentToEmail(String sentToEmail) { this.sentToEmail = sentToEmail; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String[] getTags() { return tags; }
    public void setTags(String[] tags) { this.tags = tags; }
    public String getMetadata() { return metadata; }
    public void setMetadata(String metadata) { this.metadata = metadata; }
}
