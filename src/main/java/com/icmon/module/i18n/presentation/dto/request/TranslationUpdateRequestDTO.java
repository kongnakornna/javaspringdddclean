package com.icmon.module.i18n.presentation.dto.request;

import com.icmon.module.i18n.presentation.validator.ValidTranslation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "คำขออัปเดตข้อความที่แปลแล้ว / Translation update request")
public class TranslationUpdateRequestDTO {

    @Schema(description = "รหัส UUID (กรณีอัปเดต) / UUID (for update only)")
    private UUID id;

    @NotBlank
    @Schema(description = "รหัสข้อความ / Message key", example = "job.status.open")
    private String messageKey;

    @NotBlank
    @ValidTranslation
    @Schema(description = "รหัสภาษา / Language code", example = "th")
    private String languageCode;

    @NotBlank
    @Schema(description = "ข้อความที่แปลแล้ว / Translated message", example = "เปิดใบงาน")
    private String messageText;

    @Schema(description = "บริบท / Context", example = "UI")
    private String context;

    @Schema(description = "คำอธิบาย / Description")
    private String description;
}
