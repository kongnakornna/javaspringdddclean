package com.icmon.module.inventory.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "ข้อมูลรายการนับสต็อก | Stocktake Response")
public class StocktakeResponseDTO {
    @Schema(description = "รหัส | ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;
    @Schema(description = "เลขที่นับสต็อก | Stocktake No", example = "ST-2024-0001")
    private String stocktakeNo;
    @Schema(description = "วันที่นับสต็อก | Stocktake Date", example = "2024-06-30T08:00:00")
    private LocalDateTime stocktakeDate;
    @Schema(description = "สถานะ | Status", example = "COMPLETED")
    private String status;
    @Schema(description = "รหัสผู้เริ่มนับ | Started By", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID startedBy;
    @Schema(description = "วันที่เริ่มนับ | Started At", example = "2024-06-30T08:00:00")
    private LocalDateTime startedAt;
    @Schema(description = "รหัสผู้ทำเสร็จ | Completed By", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID completedBy;
    @Schema(description = "วันที่ทำเสร็จ | Completed At", example = "2024-06-30T12:00:00")
    private LocalDateTime completedAt;
    @Schema(description = "จำนวนที่ผิดปกติรวม | Total Discrepancy", example = "5")
    private int totalDiscrepancy;
    @Schema(description = "หมายเหตุ | Notes", example = "นับสต็อกประจำเดือนมิถุนายน")
    private String notes;
    @Schema(description = "รายละเอียดการนับ | Stocktake Details")
    private List<StocktakeDetailItem> details;
    @Schema(description = "วันที่สร้าง | Created At", example = "2024-06-30T08:00:00")
    private LocalDateTime createdAt;

    @Data
    @Schema(description = "รายละเอียดการนับสต็อกย่อย | Stocktake Detail Item")
    public static class StocktakeDetailItem {
        @Schema(description = "รหัส | ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        private UUID id;
        @Schema(description = "รหัสอะไหล่ | Part ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        private UUID partId;
        @Schema(description = "จำนวนในระบบ | System Quantity", example = "50")
        private int systemQuantity;
        @Schema(description = "จำนวนที่นับได้ | Counted Quantity", example = "48")
        private int countedQuantity;
        @Schema(description = "ส่วนต่าง | Discrepancy", example = "-2")
        private int discrepancy;
        @Schema(description = "หมายเหตุ | Note", example = "นับแล้วขาด 2 ชิ้น")
        private String note;
        @Schema(description = "รหัสผู้นับ | Counted By", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        private UUID countedBy;
        @Schema(description = "วันที่นับ | Counted At", example = "2024-06-30T10:00:00")
        private LocalDateTime countedAt;
    }
}
