package com.icmon.module.i18n.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "ข้อความที่แปลแล้ว / Message response")
public class MessageResponseDTO {
    @Schema(description = "รหัสข้อความ / Message key", example = "job.status.open")
    private String messageKey;

    @Schema(description = "ข้อความที่แปลแล้ว / Message text", example = "เปิดใบงาน")
    private String messageText;

    @Schema(description = "รหัสภาษา / Language code", example = "th")
    private String languageCode;

    @Schema(description = "มาจากแคช / From cache", example = "true")
    private Boolean fromCache;
}
