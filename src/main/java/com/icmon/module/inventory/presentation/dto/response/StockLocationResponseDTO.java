package com.icmon.module.inventory.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "ข้อมูลสถานที่จัดเก็บ | Stock Location Response")
public class StockLocationResponseDTO {
    @Schema(description = "รหัส | ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;
    @Schema(description = "รหัสสถานที่ | Location Code", example = "A-01-01")
    private String locationCode;
    @Schema(description = "ชื่อสถานที่ | Location Name", example = "คลังสินค้าหลัก ชั้น 1 โซน A")
    private String locationName;
    @Schema(description = "ประเภทสถานที่ | Location Type", example = "SHELF")
    private String locationType;
    @Schema(description = "โซน | Zone", example = "A")
    private String zone;
    @Schema(description = "ความจุ | Capacity", example = "100")
    private Integer capacity;
    @Schema(description = "การใช้งานปัจจุบัน | Current Usage", example = "45")
    private Integer currentUsage;
    @Schema(description = "สถานะการใช้งาน | Is Active", example = "true")
    private boolean isActive;
    @Schema(description = "หมายเหตุ | Notes", example = "โซนอะไหล่เบรก")
    private String notes;
    @Schema(description = "วันที่สร้าง | Created At", example = "2024-01-01T08:00:00")
    private LocalDateTime createdAt;
    @Schema(description = "วันที่แก้ไขล่าสุด | Updated At", example = "2024-06-15T10:30:00")
    private LocalDateTime updatedAt;
}
