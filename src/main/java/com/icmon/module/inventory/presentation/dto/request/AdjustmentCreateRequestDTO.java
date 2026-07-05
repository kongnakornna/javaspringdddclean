package com.icmon.module.inventory.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "คำขอสร้างรายการปรับปรุงสต็อก | Adjustment Create Request")
public class AdjustmentCreateRequestDTO {
    @Schema(description = "ประเภทการปรับปรุง | Adjustment Type", example = "STOCK_ADJUSTMENT")
    private String adjustmentType;
    @Schema(description = "เหตุผล | Reason", example = "นับสต็อกประจำเดือน")
    private String reason;
    @Schema(description = "คำอธิบาย | Description", example = "ปรับปรุงยอดสินค้าคงคลังประจำเดือนมิถุนายน")
    private String description;
    @Schema(description = "รายการปรับปรุง | Adjustment Details")
    private List<AdjustmentDetailItem> details;

    @Data
    @Schema(description = "รายการปรับปรุงย่อย | Adjustment Detail Item")
    public static class AdjustmentDetailItem {
        @Schema(description = "รหัสอะไหล่ | Part ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        private String partId;
        @Schema(description = "จำนวนที่ปรับ | Quantity", example = "5")
        private int quantity;
        @Schema(description = "หมายเหตุ | Note", example = "นับแล้วพบเกิน 5 ชิ้น")
        private String note;
    }
}
