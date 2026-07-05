package com.icmon.module.dashboard.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.UUID;

@Data
@Schema(description = "คำขอตั้งค่าวิดเจ็ต / Widget configuration request")
public class WidgetConfigRequestDTO {
    @Schema(description = "รหัสผู้ใช้ / User ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID userId;
    @Schema(description = "รหัสวิดเจ็ต / Widget ID", example = "WIDGET-001")
    private String widgetId;
    @Schema(description = "ประเภทวิดเจ็ต / Widget type", example = "CHART")
    private String widgetType;
    @Schema(description = "ชื่อวิดเจ็ต / Widget title", example = "ยอดขายรายเดือน")
    private String widgetTitle;
    @Schema(description = "ตำแหน่ง / Position", example = "1")
    private Integer position;
    @Schema(description = "ความกว้าง / Width", example = "6")
    private Integer width;
    @Schema(description = "ความสูง / Height", example = "4")
    private Integer height;
    @Schema(description = "การกำหนดค่า / Configuration", example = "{\"type\":\"bar\",\"period\":\"monthly\"}")
    private String config;
}
