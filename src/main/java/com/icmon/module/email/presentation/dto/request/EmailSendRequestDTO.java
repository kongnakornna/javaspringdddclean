package com.icmon.module.email.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
@Schema(description = "คำขอส่งอีเมล // Email send request")
public class EmailSendRequestDTO {

    @Schema(description = "รหัสเทมเพลต // Template code")
    private String templateCode;

    @Schema(description = "ประเภทอ้างอิง // Reference type (QUOTATION, INVOICE, PO, etc.)")
    private String referenceType;

    @Schema(description = "ID อ้างอิง // Reference ID")
    private UUID referenceId;

    @Schema(description = "ผู้ส่ง // From email")
    private String fromEmail;

    @Schema(description = "ชื่อผู้ส่ง // From name")
    private String fromName;

    @NotBlank(message = "ผู้รับห้ามว่าง // Recipient email is required")
    @Email(message = "รูปแบบอีเมลไม่ถูกต้อง // Invalid email format")
    @Schema(description = "ผู้รับ // To email")
    private String toEmail;

    @Schema(description = "ชื่อผู้รับ // To name")
    private String toName;

    @Schema(description = "CC")
    private String ccEmail;

    @Schema(description = "BCC")
    private String bccEmail;

    @Schema(description = "หัวข้อ // Subject (required when not using template)")
    private String subject;

    @Schema(description = "เนื้อหา HTML // HTML body")
    private String bodyHtml;

    @Schema(description = "เนื้อหา Text // Text body")
    private String bodyText;

    @Schema(description = "ภาษา // Language (th, en)")
    private String language;

    @Schema(description = "ความสำคัญ // Priority (LOW, NORMAL, HIGH, URGENT)")
    private String priority;

    @Schema(description = "ตัวแปรสำหรับเทมเพลต // Template variables")
    private Map<String, String> variables;

    @Schema(description = "ไฟล์แนบ // Attachments")
    private Map<String, String> attachments;
}
