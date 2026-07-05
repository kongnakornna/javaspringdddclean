package com.icmon.module.email.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_email_history")
@Getter
@Setter
public class EmailHistoryEntity extends GenericBusinessEntity {

    @Column(name = "email_id", unique = true, nullable = false, length = 50)
    private String emailId;

    @Column(name = "template_code", length = 50)
    private String templateCode;

    @Column(name = "reference_type", length = 30)
    private String referenceType;

    @Column(name = "reference_id")
    private UUID referenceId;

    @Column(name = "from_email", nullable = false, length = 100)
    private String fromEmail;

    @Column(name = "from_name", length = 100)
    private String fromName;

    @Column(name = "to_email", nullable = false, length = 200)
    private String toEmail;

    @Column(name = "to_name", length = 100)
    private String toName;

    @Column(name = "cc_email", length = 200)
    private String ccEmail;

    @Column(name = "bcc_email", length = 200)
    private String bccEmail;

    @Column(nullable = false, length = 255)
    private String subject;

    @Column(name = "body_preview", columnDefinition = "TEXT")
    private String bodyPreview;

    @Column(length = 20)
    private String status;

    @Column(length = 20)
    private String priority;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "retry_count")
    private Integer retryCount = 0;

    @Column(columnDefinition = "jsonb")
    private String attachments;

    @Column(columnDefinition = "jsonb")
    private String metadata;
}
