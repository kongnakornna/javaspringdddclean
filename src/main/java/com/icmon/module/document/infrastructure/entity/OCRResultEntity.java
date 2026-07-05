package com.icmon.module.document.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "t_ocr_result")
@Getter
@Setter
public class OCRResultEntity extends GenericBusinessEntity {

    @Column(name = "document_id")
    private UUID documentId;

    @Column(name = "image_url", nullable = false, columnDefinition = "TEXT")
    private String imageUrl;

    @Column(nullable = false, length = 30)
    private String provider;

    @Column(name = "extracted_text", columnDefinition = "TEXT")
    private String extractedText;

    @Column(name = "confidence_score", precision = 5, scale = 2)
    private BigDecimal confidenceScore;

    @Column(length = 10)
    private String language;

    @Column(name = "processing_time_ms")
    private Integer processingTimeMs;

    @Column(columnDefinition = "jsonb")
    private String metadata;

    @Column(length = 20)
    private String status;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;
}
