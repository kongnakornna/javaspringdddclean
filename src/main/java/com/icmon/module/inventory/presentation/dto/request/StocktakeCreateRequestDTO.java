package com.icmon.module.inventory.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "คำขอสร้างรายการนับสต็อก | Stocktake Create Request")
public class StocktakeCreateRequestDTO {
    @Schema(description = "รหัสผู้เริ่มนับ | Started By", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID startedBy;
    @Schema(description = "หมายเหตุ | Notes", example = "นับสต็อกประจำเดือนสิงหาคม")
    private String notes;
}
