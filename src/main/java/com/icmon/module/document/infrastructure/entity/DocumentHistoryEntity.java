package com.icmon.module.document.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_document_history")
@Getter
@Setter
public class DocumentHistoryEntity extends GenericBusinessEntity {

    @Column(name = "document_id", nullable = false)
    private UUID documentId;

    @Column(nullable = false, length = 30)
    private String action;

    @Column(name = "performed_by", nullable = false)
    private UUID performedBy;

    @Column(name = "performed_at", nullable = false)
    private LocalDateTime performedAt;

    @Column(columnDefinition = "TEXT")
    private String details;
}
