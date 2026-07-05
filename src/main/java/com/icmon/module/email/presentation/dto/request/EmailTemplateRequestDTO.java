package com.icmon.module.email.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "คำขอสร้าง/แก้ไขเทมเพลต // Email template request")
public class EmailTemplateRequestDTO {

    @NotBlank(message = "รหัสเทมเพลตห้ามว่าง // Template code is required")
    @Schema(description = "รหัสเทมเพลต // Template code", example = "INV-EMAIL-001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String templateCode;

    @NotBlank(message = "ชื่อเทมเพลตห้ามว่าง // Template name is required")
    @Schema(description = "ชื่อเทมเพลต // Template name", example = "Invoice Email Template", requiredMode = Schema.RequiredMode.REQUIRED)
    private String templateName;

    @NotBlank(message = "หัวข้อห้ามว่าง // Subject is required")
    @Schema(description = "หัวข้อ // Subject", example = "Invoice INV-2026-0001 from ICMON", requiredMode = Schema.RequiredMode.REQUIRED)
    private String subject;

    @Schema(description = "เนื้อหา HTML // HTML body", example = "<h1>Invoice</h1><p>Dear {{customerName}}...</p>")
    private String bodyHtml;

    @Schema(description = "เนื้อหา Text // Text body", example = "Invoice INV-2026-0001 - Dear {{customerName}}...")
    private String bodyText;

    @Schema(description = "ผู้ส่ง // From email", example = "noreply@icmon.com")
    private String fromEmail;

    @Schema(description = "ชื่อผู้ส่ง // From name", example = "ICMON System")
    private String fromName;

    @Schema(description = "ประเภท // Category", example = "INVOICE")
    private String category;

    @Schema(description = "ภาษา // Language (th, en)", example = "th")
    private String language;

    @Schema(description = "ตัวแปรที่ใช้ในเทมเพลต // Template variables (JSON)", example = "{\"customerName\": \"string\", \"amount\": \"string\"}")
    private String variables;

    @Schema(description = "คำอธิบาย // Description", example = "Template for sending invoice emails")
    private String description;
}
