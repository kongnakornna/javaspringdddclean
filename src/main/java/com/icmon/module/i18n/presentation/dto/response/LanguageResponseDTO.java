package com.icmon.module.i18n.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Schema(description = "ข้อมูลภาษา / Language response")
public class LanguageResponseDTO {
    @Schema(description = "รหัสภาษา / Language ID")
    private UUID id;

    @Schema(description = "รหัสภาษา / Language code", example = "en")
    private String languageCode;

    @Schema(description = "ชื่อภาษา / Language name", example = "English")
    private String languageName;

    @Schema(description = "ชื่อภาษาอังกฤษ / Language name (English)", example = "English")
    private String languageNameEn;

    @Schema(description = "ธง / Flag emoji", example = "🇬🇧")
    private String flagEmoji;

    @Schema(description = "อ่านจากขวาไปซ้าย / Right-to-left", example = "false")
    private Boolean isRtl;

    @Schema(description = "เปิดใช้งาน / Active", example = "true")
    private Boolean isActive;

    @Schema(description = "ภาษาเริ่มต้น / Default language", example = "false")
    private Boolean isDefault;

    @Schema(description = "ลำดับ / Sort order")
    private Integer sortOrder;

    @Schema(description = "Locale", example = "en_US")
    private String locale;

    @Schema(description = "รูปแบบวันที่ / Date format", example = "dd/MM/yyyy")
    private String dateFormat;

    @Schema(description = "รูปแบบเวลา / Time format", example = "HH:mm")
    private String timeFormat;

    @Schema(description = "รูปแบบตัวเลข / Number format", example = "#,##0.00")
    private String numberFormat;

    @Schema(description = "สัญลักษณ์สกุลเงิน / Currency symbol", example = "$")
    private String currencySymbol;

    @Schema(description = "สร้างเมื่อ / Created at")
    private LocalDateTime createdAt;

    @Schema(description = "อัปเดตเมื่อ / Updated at")
    private LocalDateTime updatedAt;
}
