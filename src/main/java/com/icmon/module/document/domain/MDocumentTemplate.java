package com.icmon.module.document.domain;

import com.icmon._shared.domain.GenericBusinessClass;

public class MDocumentTemplate extends GenericBusinessClass {
    private String templateCode;
    private String templateName;
    private String templateType;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private Integer version;
    private String description;
    private Boolean isActive;
    private Boolean isDefault;
    private String parameters;

    public void incrementVersion() {
        this.version = (this.version != null ? this.version : 0) + 1;
    }

    public boolean isUsable() {
        return this.isActive != null && this.isActive && this.filePath != null;
    }

    public String getTemplateCode() { return templateCode; }
    public void setTemplateCode(String templateCode) { this.templateCode = templateCode; }
    public String getTemplateName() { return templateName; }
    public void setTemplateName(String templateName) { this.templateName = templateName; }
    public String getTemplateType() { return templateType; }
    public void setTemplateType(String templateType) { this.templateType = templateType; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }
    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Boolean getIsActive() { return isActive; }
    public void setActive(Boolean active) { isActive = active; }
    public Boolean getIsDefault() { return isDefault; }
    public void setDefault(Boolean aDefault) { isDefault = aDefault; }
    public String getParameters() { return parameters; }
    public void setParameters(String parameters) { this.parameters = parameters; }
}
