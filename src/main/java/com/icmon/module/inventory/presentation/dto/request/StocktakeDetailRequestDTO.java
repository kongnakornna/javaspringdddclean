package com.icmon.module.inventory.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "คำขอรายละเอียดการนับสต็อก | Stocktake Detail Request")
public class StocktakeDetailRequestDTO {
    @Schema(description = "รหัสอะไหล่ | Part ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID partId;
    @Schema(description = "จำนวนในระบบ | System Quantity", example = "50")
    private int systemQuantity;
    @Schema(description = "จำนวนที่นับได้ | Counted Quantity", example = "48")
    private int countedQuantity;
    @Schema(description = "หมายเหตุ | Note", example = "นับแล้วขาด 2 ชิ้น")
    private String note;
    @Schema(description = "รหัสผู้นับ | Counted By", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID countedBy;
}
