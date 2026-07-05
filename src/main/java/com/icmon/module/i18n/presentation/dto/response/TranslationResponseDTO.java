package com.icmon.module.i18n.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Schema(description = "ข้อมูลการแปล / Translation response")
public class TranslationResponseDTO {
    @Schema(description = "รหัสการแปล / Translation ID")
    private UUID id;

    @Schema(description = "รหัสข้อความ / Message key", example = "job.status.open")
    private String messageKey;

    @Schema(description = "รหัสภาษา / Language code", example = "th")
    private String languageCode;

    @Schema(description = "ข้อความที่แปลแล้ว / Translated message", example = "เปิดใบงาน")
    private String messageText;

    @Schema(description = "บริบท / Context", example = "UI")
    private String context;

    @Schema(description = "คำอธิบาย / Description")
    private String description;

    @Schema(description = "เวอร์ชัน / Version")
    private Integer version;

    @Schema(description = "อนุมัติแล้ว / Approved", example = "true")
    private Boolean isApproved;

    @Schema(description = "ผู้อนุมัติ / Approved by")
    private UUID approvedBy;

    @Schema(description = "วันที่อนุมัติ / Approved at")
    private LocalDateTime approvedAt;

    @Schema(description = "สร้างเมื่อ / Created at")
    private LocalDateTime createdAt;

    @Schema(description = "อัปเดตเมื่อ / Updated at")
    private LocalDateTime updatedAt;
}
