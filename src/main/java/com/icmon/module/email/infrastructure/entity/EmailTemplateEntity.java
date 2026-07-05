package com.icmon.module.email.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "m_email_template")
@Getter
@Setter
public class EmailTemplateEntity extends GenericBusinessEntity {

    @Column(name = "template_code", unique = true, nullable = false, length = 50)
    private String templateCode;

    @Column(name = "template_name", nullable = false, length = 100)
    private String templateName;

    @Column(nullable = false, length = 255)
    private String subject;

    @Column(name = "body_html", columnDefinition = "TEXT")
    private String bodyHtml;

    @Column(name = "body_text", columnDefinition = "TEXT")
    private String bodyText;

    @Column(name = "from_email", length = 100)
    private String fromEmail;

    @Column(name = "from_name", length = 100)
    private String fromName;

    @Column(length = 50)
    private String category;

    @Column(length = 10)
    private String language;

    @Column(nullable = false)
    private Integer version = 1;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "is_default")
    private Boolean isDefault = false;

    @Column(columnDefinition = "jsonb")
    private String variables;

    @Column(columnDefinition = "TEXT")
    private String description;
}
