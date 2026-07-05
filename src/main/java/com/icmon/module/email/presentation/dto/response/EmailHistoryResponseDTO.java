package com.icmon.module.email.presentation.dto.response;

import com.icmon.module.email.infrastructure.entity.EmailHistoryEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "ประวัติการส่งอีเมล // Email history response")
public class EmailHistoryResponseDTO {

    @Schema(description = "ID // ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID id;

    @Schema(description = "Email ID // Email ID", example = "email-20260705-001")
    private String emailId;

    @Schema(description = "รหัสเทมเพลต // Template code", example = "INV-EMAIL-001")
    private String templateCode;

    @Schema(description = "ประเภทอ้างอิง // Reference type", example = "INVOICE")
    private String referenceType;

    @Schema(description = "ID อ้างอิง // Reference ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID referenceId;

    @Schema(description = "ผู้ส่ง // From email", example = "noreply@icmon.com")
    private String fromEmail;

    @Schema(description = "ผู้รับ // To email", example = "customer@example.com")
    private String toEmail;

    @Schema(description = "ชื่อผู้รับ // To name", example = "สมชาย ใจดี")
    private String toName;

    @Schema(description = "CC // CC", example = "manager@example.com")
    private String ccEmail;

    @Schema(description = "BCC // BCC", example = "archive@example.com")
    private String bccEmail;

    @Schema(description = "หัวข้อ // Subject", example = "Invoice INV-2026-0001 from ICMON")
    private String subject;

    @Schema(description = "สถานะ // Status", example = "SENT")
    private String status;

    @Schema(description = "ความสำคัญ // Priority", example = "NORMAL")
    private String priority;

    @Schema(description = "วันที่ส่ง // Sent at", example = "2026-07-05T10:30:00")
    private LocalDateTime sentAt;

    @Schema(description = "ข้อความผิดพลาด // Error message", example = "Connection timeout")
    private String errorMessage;

    @Schema(description = "จำนวนลองใหม่ // Retry count", example = "3")
    private Integer retryCount;

    @Schema(description = "วันที่สร้าง // Created at", example = "2026-07-05T10:30:00")
    private LocalDateTime createdAt;

    public static EmailHistoryResponseDTO fromEntity(EmailHistoryEntity entity) {
        EmailHistoryResponseDTO dto = new EmailHistoryResponseDTO();
        dto.setId(entity.getId());
        dto.setEmailId(entity.getEmailId());
        dto.setTemplateCode(entity.getTemplateCode());
        dto.setReferenceType(entity.getReferenceType());
        dto.setReferenceId(entity.getReferenceId());
        dto.setFromEmail(entity.getFromEmail());
        dto.setToEmail(entity.getToEmail());
        dto.setToName(entity.getToName());
        dto.setCcEmail(entity.getCcEmail());
        dto.setBccEmail(entity.getBccEmail());
        dto.setSubject(entity.getSubject());
        dto.setStatus(entity.getStatus());
        dto.setPriority(entity.getPriority());
        dto.setSentAt(entity.getSentAt());
        dto.setErrorMessage(entity.getErrorMessage());
        dto.setRetryCount(entity.getRetryCount());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
