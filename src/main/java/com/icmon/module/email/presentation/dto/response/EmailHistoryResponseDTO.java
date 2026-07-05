package com.icmon.module.email.presentation.dto.response;

import com.icmon.module.email.infrastructure.entity.EmailHistoryEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "ประวัติการส่งอีเมล // Email history response")
public class EmailHistoryResponseDTO {

    @Schema(description = "ID")
    private UUID id;

    @Schema(description = "Email ID")
    private String emailId;

    @Schema(description = "รหัสเทมเพลต // Template code")
    private String templateCode;

    @Schema(description = "ประเภทอ้างอิง // Reference type")
    private String referenceType;

    @Schema(description = "ID อ้างอิง // Reference ID")
    private UUID referenceId;

    @Schema(description = "ผู้ส่ง // From email")
    private String fromEmail;

    @Schema(description = "ผู้รับ // To email")
    private String toEmail;

    @Schema(description = "ชื่อผู้รับ // To name")
    private String toName;

    @Schema(description = "CC")
    private String ccEmail;

    @Schema(description = "BCC")
    private String bccEmail;

    @Schema(description = "หัวข้อ // Subject")
    private String subject;

    @Schema(description = "สถานะ // Status")
    private String status;

    @Schema(description = "ความสำคัญ // Priority")
    private String priority;

    @Schema(description = "วันที่ส่ง // Sent at")
    private LocalDateTime sentAt;

    @Schema(description = "ข้อความผิดพลาด // Error message")
    private String errorMessage;

    @Schema(description = "จำนวนลองใหม่ // Retry count")
    private Integer retryCount;

    @Schema(description = "วันที่สร้าง // Created at")
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
