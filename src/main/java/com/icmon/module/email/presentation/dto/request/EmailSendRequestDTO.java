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

    @Schema(description = "รหัสเทมเพลต // Template code", example = "INV-EMAIL-001")
    private String templateCode;

    @Schema(description = "ประเภทอ้างอิง // Reference type (QUOTATION, INVOICE, PO, etc.)", example = "INVOICE")
    private String referenceType;

    @Schema(description = "ID อ้างอิง // Reference ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID referenceId;

    @Schema(description = "ผู้ส่ง // From email", example = "noreply@icmon.com")
    private String fromEmail;

    @Schema(description = "ชื่อผู้ส่ง // From name", example = "ICMON System")
    private String fromName;

    @NotBlank(message = "ผู้รับห้ามว่าง // Recipient email is required")
    @Email(message = "รูปแบบอีเมลไม่ถูกต้อง // Invalid email format")
    @Schema(description = "ผู้รับ // To email", example = "customer@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String toEmail;

    @Schema(description = "ชื่อผู้รับ // To name", example = "สมชาย ใจดี")
    private String toName;

    @Schema(description = "CC // CC", example = "manager@example.com")
    private String ccEmail;

    @Schema(description = "BCC // BCC", example = "archive@example.com")
    private String bccEmail;

    @Schema(description = "หัวข้อ // Subject (required when not using template)", example = "Invoice INV-2026-0001")
    private String subject;

    @Schema(description = "เนื้อหา HTML // HTML body", example = "<h1>Invoice</h1><p>Dear customer...</p>")
    private String bodyHtml;

    @Schema(description = "เนื้อหา Text // Text body", example = "Invoice INV-2026-0001 - Dear customer...")
    private String bodyText;

    @Schema(description = "ภาษา // Language (th, en)", example = "th")
    private String language;

    @Schema(description = "ความสำคัญ // Priority (LOW, NORMAL, HIGH, URGENT)", example = "NORMAL")
    private String priority;

    @Schema(description = "ตัวแปรสำหรับเทมเพลต // Template variables", example = "{\"customerName\": \"สมชาย ใจดี\", \"amount\": \"5000\"}")
    private Map<String, String> variables;

    @Schema(description = "ไฟล์แนบ // Attachments", example = "{\"invoice.pdf\": \"https://storage.icmon.com/inv-2026-0001.pdf\"}")
    private Map<String, String> attachments;
}
