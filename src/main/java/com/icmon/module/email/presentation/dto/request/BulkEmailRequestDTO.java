package com.icmon.module.email.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Schema(description = "คำขอส่งอีเมลจำนวนมาก // Bulk email send request")
public class BulkEmailRequestDTO {

    @NotBlank(message = "รหัสเทมเพลตห้ามว่าง // Template code is required")
    @Schema(description = "รหัสเทมเพลต // Template code", example = "INV-EMAIL-001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String templateCode;

    @Schema(description = "ภาษา // Language (th, en)", example = "th")
    private String language;

    @Schema(description = "ความสำคัญ // Priority (LOW, NORMAL, HIGH, URGENT)", example = "NORMAL")
    private String priority;

    @NotEmpty(message = "รายชื่อผู้รับห้ามว่าง // Recipients list is required")
    @Valid
    @Schema(description = "รายชื่อผู้รับ // Recipients", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Recipient> recipients;

    @Data
    @Schema(description = "ผู้รับ // Recipient")
    public static class Recipient {

        @NotBlank(message = "อีเมลผู้รับห้ามว่าง // Recipient email is required")
        @Schema(description = "อีเมล // Email", example = "customer@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
        private String email;

        @Schema(description = "ชื่อ // Name", example = "สมชาย ใจดี")
        private String name;

        @Schema(description = "ตัวแปรเฉพาะ // Per-recipient variables", example = "{\"amount\": \"5000\"}")
        private Map<String, String> variables;
    }
}
