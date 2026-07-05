package com.icmon.module.email.presentation.dto.response;

import com.icmon.module.email.infrastructure.entity.EmailHistoryEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "ผลลัพธ์การส่งอีเมล // Email send response")
public class EmailSendResponseDTO {

    @Schema(description = "ID")
    private UUID id;

    @Schema(description = "Email ID")
    private String emailId;

    @Schema(description = "ผู้รับ // To email")
    private String toEmail;

    @Schema(description = "หัวข้อ // Subject")
    private String subject;

    @Schema(description = "สถานะ // Status")
    private String status;

    @Schema(description = "วันที่ส่ง // Sent at")
    private LocalDateTime sentAt;

    @Schema(description = "ข้อความ // Message")
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
