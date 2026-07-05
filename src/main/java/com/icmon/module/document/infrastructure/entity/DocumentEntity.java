package com.icmon.module.document.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_document")
@Getter
@Setter
public class DocumentEntity extends GenericBusinessEntity {

    @Column(name = "document_no", unique = true, nullable = false, length = 30)
    private String documentNo;

    @Column(name = "document_type", nullable = false, length = 20)
    private String documentType;

    @Column(name = "document_sub_type", length = 30)
    private String documentSubType;

    @Column(name = "reference_type", length = 30)
    private String referenceType;

    @Column(name = "reference_id")
    private UUID referenceId;

    @Column(name = "template_id")
    private UUID templateId;

    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    @Column(name = "file_path", nullable = false, columnDefinition = "TEXT")
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "mime_type", length = 50)
    private String mimeType;

    @Column(length = 20)
    private String status;

    @Column(name = "generated_by", nullable = false)
    private UUID generatedBy;

    @Column(name = "generated_at", nullable = false)
    private LocalDateTime generatedAt;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    @Column(name = "sent_by")
    private UUID sentBy;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "sent_to_email", length = 200)
    private String sentToEmail;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT[]")
    private String[] tags;

    @Column(columnDefinition = "jsonb")
    private String metadata;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
