package com.icmon.module.inventory.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "ข้อมูลรายการเบิกสินค้า | Picking Response")
public class PickingResponseDTO {
    @Schema(description = "รหัส | ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;
    @Schema(description = "เลขที่เบิก | Picking No", example = "PK-2024-0001")
    private String pickingNo;
    @Schema(description = "รหัสงาน | Job ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID jobId;
    @Schema(description = "รหัสใบเสนอราคา | Quotation ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID quotationId;
    @Schema(description = "วันที่ขอเบิก | Requested Date", example = "2024-07-01T09:00:00")
    private LocalDateTime requestedDate;
    @Schema(description = "รหัสผู้ขอเบิก | Requested By", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID requestedBy;
    @Schema(description = "สถานะ | Status", example = "PENDING")
    private String status;
    @Schema(description = "ลำดับความสำคัญ | Priority", example = "HIGH")
    private String priority;
    @Schema(description = "หมายเหตุ | Notes", example = "งานด่วนต้องใช้ภายในวันนี้")
    private String notes;
    @Schema(description = "รหัสผู้เบิก | Picked By", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID pickedBy;
    @Schema(description = "วันที่เบิก | Picked Date", example = "2024-07-01T10:00:00")
    private LocalDateTime pickedDate;
    @Schema(description = "รหัสผู้ยืนยัน | Confirmed By", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID confirmedBy;
    @Schema(description = "วันที่ยืนยัน | Confirmed Date", example = "2024-07-01T11:00:00")
    private LocalDateTime confirmedDate;
    @Schema(description = "รายการสินค้า | Picking Items")
    private List<PickingItem> items;
    @Schema(description = "วันที่สร้าง | Created At", example = "2024-07-01T09:00:00")
    private LocalDateTime createdAt;

    @Data
    @Schema(description = "รายการเบิกสินค้าย่อย | Picking Item")
    public static class PickingItem {
        @Schema(description = "รหัส | ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        private UUID id;
        @Schema(description = "รหัสอะไหล่ | Part ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        private UUID partId;
        @Schema(description = "จำนวนที่ขอเบิก | Requested Quantity", example = "10")
        private int requestedQuantity;
        @Schema(description = "จำนวนที่เบิกจริง | Picked Quantity", example = "10")
        private int pickedQuantity;
        @Schema(description = "หมายเหตุ | Note", example = "เบิกครบตามจำนวน")
        private String note;
    }
}
