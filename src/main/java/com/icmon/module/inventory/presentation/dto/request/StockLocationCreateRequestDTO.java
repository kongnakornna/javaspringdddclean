package com.icmon.module.inventory.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "คำขอสร้างข้อมูลสถานที่จัดเก็บ | Stock Location Create Request")
public class StockLocationCreateRequestDTO {
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
    @Schema(description = "หมายเหตุ | Notes", example = "โซนอะไหล่เบรก")
    private String notes;
}
