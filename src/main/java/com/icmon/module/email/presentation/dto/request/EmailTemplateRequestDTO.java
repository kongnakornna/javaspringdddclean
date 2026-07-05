package com.icmon.module.email.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "คำขอสร้าง/แก้ไขเทมเพลต // Email template request")
public class EmailTemplateRequestDTO {

    @NotBlank(message = "รหัสเทมเพลตห้ามว่าง // Template code is required")
    @Schema(description = "รหัสเทมเพลต // Template code")
    private String templateCode;

    @NotBlank(message = "ชื่อเทมเพลตห้ามว่าง // Template name is required")
    @Schema(description = "ชื่อเทมเพลต // Template name")
    private String templateName;

    @NotBlank(message = "หัวข้อห้ามว่าง // Subject is required")
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

    @Schema(description = "ภาษา // Language (th, en)")
    private String language;

    @Schema(description = "ตัวแปรที่ใช้ในเทมเพลต // Template variables (JSON)")
    private String variables;

    @Schema(description = "คำอธิบาย // Description")
    private String description;
}
