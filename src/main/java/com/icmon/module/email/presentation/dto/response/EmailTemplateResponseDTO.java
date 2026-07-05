package com.icmon.module.email.presentation.dto.response;

import com.icmon.module.email.infrastructure.entity.EmailTemplateEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "ข้อมูลเทมเพลตอีเมล // Email template response")
public class EmailTemplateResponseDTO {

    @Schema(description = "ID // ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID id;

    @Schema(description = "รหัสเทมเพลต // Template code", example = "INV-EMAIL-001")
    private String templateCode;

    @Schema(description = "ชื่อเทมเพลต // Template name", example = "Invoice Email Template")
    private String templateName;

    @Schema(description = "หัวข้อ // Subject", example = "Invoice INV-2026-0001 from ICMON")
    private String subject;

    @Schema(description = "เนื้อหา HTML // HTML body", example = "<h1>Invoice</h1><p>Dear customer...</p>")
    private String bodyHtml;

    @Schema(description = "เนื้อหา Text // Text body", example = "Invoice INV-2026-0001 - Dear customer...")
    private String bodyText;

    @Schema(description = "ผู้ส่ง // From email", example = "noreply@icmon.com")
    private String fromEmail;

    @Schema(description = "ชื่อผู้ส่ง // From name", example = "ICMON System")
    private String fromName;

    @Schema(description = "ประเภท // Category", example = "INVOICE")
    private String category;

    @Schema(description = "ภาษา // Language", example = "th")
    private String language;

    @Schema(description = "เวอร์ชัน // Version", example = "1")
    private Integer version;

    @Schema(description = "สถานะ active // Is active", example = "true")
    private Boolean isActive;

    @Schema(description = "ค่าเริ่มต้น // Is default", example = "false")
    private Boolean isDefault;

    @Schema(description = "ตัวแปร // Variables (JSON)", example = "{\"customerName\": \"string\", \"amount\": \"string\"}")
    private String variables;

    @Schema(description = "คำอธิบาย // Description", example = "Template for sending invoice emails")
    private String description;

    @Schema(description = "วันที่สร้าง // Created at", example = "2026-07-05T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "วันที่อัปเดต // Updated at", example = "2026-07-05T10:30:00")
    private LocalDateTime updatedAt;

    public static EmailTemplateResponseDTO fromEntity(EmailTemplateEntity entity) {
        EmailTemplateResponseDTO dto = new EmailTemplateResponseDTO();
        dto.setId(entity.getId());
        dto.setTemplateCode(entity.getTemplateCode());
        dto.setTemplateName(entity.getTemplateName());
        dto.setSubject(entity.getSubject());
        dto.setBodyHtml(entity.getBodyHtml());
        dto.setBodyText(entity.getBodyText());
        dto.setFromEmail(entity.getFromEmail());
        dto.setFromName(entity.getFromName());
        dto.setCategory(entity.getCategory());
        dto.setLanguage(entity.getLanguage());
        dto.setVersion(entity.getVersion());
        dto.setIsActive(entity.getIsActive());
        dto.setIsDefault(entity.getIsDefault());
        dto.setVariables(entity.getVariables());
        dto.setDescription(entity.getDescription());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
