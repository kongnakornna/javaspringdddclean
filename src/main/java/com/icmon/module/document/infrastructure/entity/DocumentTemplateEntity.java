package com.icmon.module.document.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "m_document_template")
@Getter
@Setter
public class DocumentTemplateEntity extends GenericBusinessEntity {

    @Column(name = "template_code", unique = true, nullable = false, length = 50)
    private String templateCode;

    @Column(name = "template_name", nullable = false, length = 100)
    private String templateName;

    @Column(name = "template_type", nullable = false, length = 20)
    private String templateType;

    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    @Column(name = "file_path", nullable = false, columnDefinition = "TEXT")
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column
    private Integer version;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_default")
    private Boolean isDefault;

    @Column(columnDefinition = "jsonb")
    private String parameters;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    @Column(name = "preview_image_url", columnDefinition = "TEXT")
    private String previewImageUrl;
}
