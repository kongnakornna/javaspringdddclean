package com.icmon.module.email.presentation.dto.response;

import com.icmon.module.email.infrastructure.entity.EmailHistoryEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "ผลลัพธ์การส่งอีเมล // Email send response")
public class EmailSendResponseDTO {

    @Schema(description = "ID // ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID id;

    @Schema(description = "Email ID // Email ID", example = "email-20260705-001")
    private String emailId;

    @Schema(description = "ผู้รับ // To email", example = "customer@example.com")
    private String toEmail;

    @Schema(description = "หัวข้อ // Subject", example = "Invoice INV-2026-0001 from ICMON")
    private String subject;

    @Schema(description = "สถานะ // Status", example = "SENT")
    private String status;

    @Schema(description = "วันที่ส่ง // Sent at", example = "2026-07-05T10:30:00")
    private LocalDateTime sentAt;

    @Schema(description = "ข้อความ // Message", example = "Email sent successfully")
    private String message;

    public static EmailSendResponseDTO fromEntity(EmailHistoryEntity entity) {
        EmailSendResponseDTO dto = new EmailSendResponseDTO();
        dto.setId(entity.getId());
        dto.setEmailId(entity.getEmailId());
        dto.setToEmail(entity.getToEmail());
        dto.setSubject(entity.getSubject());
        dto.setStatus(entity.getStatus());
        dto.setSentAt(entity.getSentAt());
        dto.setMessage("Email sent successfully");
        return dto;
    }
}
