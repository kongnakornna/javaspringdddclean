package com.icmon.module.inventory.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "ข้อมูลรายการปรับปรุงสต็อก | Adjustment Response")
public class AdjustmentResponseDTO {
    @Schema(description = "รหัส | ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;
    @Schema(description = "เลขที่ปรับปรุง | Adjustment No", example = "ADJ-2024-0001")
    private String adjustmentNo;
    @Schema(description = "วันที่ปรับปรุง | Adjustment Date", example = "2024-06-30T08:00:00")
    private LocalDateTime adjustmentDate;
    @Schema(description = "ประเภทการปรับปรุง | Adjustment Type", example = "STOCK_ADJUSTMENT")
    private String adjustmentType;
    @Schema(description = "เหตุผล | Reason", example = "นับสต็อกประจำเดือน")
    private String reason;
    @Schema(description = "สถานะ | Status", example = "APPROVED")
    private String status;
    @Schema(description = "คำอธิบาย | Description", example = "ปรับปรุงยอดสินค้าคงคลังประจำเดือนมิถุนายน")
    private String description;
    @Schema(description = "รหัสผู้อนุมัติ | Approved By", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID approvedBy;
    @Schema(description = "วันที่อนุมัติ | Approved At", example = "2024-06-30T10:00:00")
    private LocalDateTime approvedAt;
    @Schema(description = "มูลค่าการปรับปรุงรวม | Total Adjustment Value", example = "2500.00")
    private BigDecimal totalAdjustmentValue;
    @Schema(description = "รายการปรับปรุง | Adjustment Details")
    private List<AdjustmentDetailItem> details;
    @Schema(description = "วันที่สร้าง | Created At", example = "2024-06-30T08:00:00")
    private LocalDateTime createdAt;

    @Data
    @Schema(description = "รายการปรับปรุงย่อย | Adjustment Detail Item")
    public static class AdjustmentDetailItem {
        @Schema(description = "รหัส | ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        private UUID id;
        @Schema(description = "รหัสอะไหล่ | Part ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        private UUID partId;
        @Schema(description = "จำนวนที่ปรับ | Quantity", example = "5")
        private int quantity;
        @Schema(description = "หมายเหตุ | Note", example = "นับแล้วพบเกิน 5 ชิ้น")
        private String note;
    }
}
