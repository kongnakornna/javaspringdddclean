package com.icmon.module.email.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import java.util.Map;

public class MEmailTemplate extends GenericBusinessClass {

    private String templateCode;
    private String templateName;
    private String subject;
    private String bodyHtml;
    private String bodyText;
    private String fromEmail;
    private String fromName;
    private String category;
    private String language;
    private Integer version;
    private Boolean isActive;
    private Boolean isDefault;
    private String variables;
    private String description;

    public String renderSubject(Map<String, String> values) {
        String result = this.subject;
        for (Map.Entry<String, String> entry : values.entrySet()) {
            result = result.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return result;
    }

    public String renderBodyHtml(Map<String, String> values) {
        if (this.bodyHtml == null) return null;
        String result = this.bodyHtml;
        for (Map.Entry<String, String> entry : values.entrySet()) {
            result = result.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return result;
    }

    public String renderBodyText(Map<String, String> values) {
        if (this.bodyText == null) return null;
        String result = this.bodyText;
        for (Map.Entry<String, String> entry : values.entrySet()) {
            result = result.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return result;
    }

    public String getTemplateCode() { return templateCode; }
    public void setTemplateCode(String templateCode) { this.templateCode = templateCode; }
    public String getTemplateName() { return templateName; }
    public void setTemplateName(String templateName) { this.templateName = templateName; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getBodyHtml() { return bodyHtml; }
    public void setBodyHtml(String bodyHtml) { this.bodyHtml = bodyHtml; }
    public String getBodyText() { return bodyText; }
    public void setBodyText(String bodyText) { this.bodyText = bodyText; }
    public String getFromEmail() { return fromEmail; }
    public void setFromEmail(String fromEmail) { this.fromEmail = fromEmail; }
    public String getFromName() { return fromName; }
    public void setFromName(String fromName) { this.fromName = fromName; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
    public Boolean getIsActive() { return isActive; }
    public void setActive(Boolean active) { isActive = active; }
    public Boolean getIsDefault() { return isDefault; }
    public void setDefault(Boolean aDefault) { isDefault = aDefault; }
    public String getVariables() { return variables; }
    public void setVariables(String variables) { this.variables = variables; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
