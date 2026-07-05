package com.icmon.module.i18n.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "m_translation", uniqueConstraints = @UniqueConstraint(columnNames = {"message_key", "language_code", "context"}))
@Getter
@Setter
public class TranslationEntity extends GenericBusinessEntity {

    @Column(name = "message_key", nullable = false, length = 255)
    private String messageKey;

    @Column(name = "language_code", nullable = false, length = 10)
    private String languageCode;

    @Lob
    @Column(name = "message_text", nullable = false, columnDefinition = "TEXT")
    private String messageText;

    @Column(length = 100)
    private String context;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private Integer version;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "approved_by", columnDefinition = "UUID")
    private UUID approvedBy;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;
}
