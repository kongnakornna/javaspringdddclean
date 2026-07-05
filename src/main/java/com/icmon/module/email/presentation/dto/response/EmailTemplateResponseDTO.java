package com.icmon.module.email.presentation.dto.response;

import com.icmon.module.email.infrastructure.entity.EmailTemplateEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "ข้อมูลเทมเพลตอีเมล // Email template response")
public class EmailTemplateResponseDTO {

    @Schema(description = "ID")
    private UUID id;

    @Schema(description = "รหัสเทมเพลต // Template code")
    private String templateCode;

    @Schema(description = "ชื่อเทมเพลต // Template name")
    private String templateName;

    @Schema(description = "หัวข้อ // Subject")
    private String subject;

    @Schema(description = "เนื้อหา HTML // HTML body")
    private String bodyHtml;

    @Schema(description = "เนื้อหา Text // Text body")
    private String bodyText;

    @Schema(description = "ผู้ส่ง // From email")
    private String fromEmail;

    @Schema(description = "ชื่อผู้ส่ง // From name")
    private String fromName;

    @Schema(description = "ประเภท // Category")
    private String category;

    @Schema(description = "ภาษา // Language")
    private String language;

    @Schema(description = "เวอร์ชัน // Version")
    private Integer version;

    @Schema(description = "สถานะ active // Is active")
    private Boolean isActive;

    @Schema(description = "ค่าเริ่มต้น // Is default")
    private Boolean isDefault;

    @Schema(description = "ตัวแปร // Variables (JSON)")
    private String variables;

    @Schema(description = "คำอธิบาย // Description")
    private String description;

    @Schema(description = "วันที่สร้าง // Created at")
    private LocalDateTime createdAt;

    @Schema(description = "วันที่อัปเดต // Updated at")
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
