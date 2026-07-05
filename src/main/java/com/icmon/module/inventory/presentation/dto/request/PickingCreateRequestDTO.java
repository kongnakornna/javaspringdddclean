package com.icmon.module.inventory.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "คำขอสร้างรายการเบิกสินค้า | Picking Create Request")
public class PickingCreateRequestDTO {
    @Schema(description = "รหัสงาน | Job ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID jobId;
    @Schema(description = "รหัสใบเสนอราคา | Quotation ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID quotationId;
    @Schema(description = "รหัสผู้ขอเบิก | Requested By", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID requestedBy;
    @Schema(description = "ลำดับความสำคัญ | Priority", example = "HIGH")
    private String priority;
    @Schema(description = "หมายเหตุ | Notes", example = "งานด่วนต้องใช้ภายในวันนี้")
    private String notes;
    @Schema(description = "รายการสินค้าที่เบิก | Picking Items")
    private List<PickingItem> items;

    @Data
    @Schema(description = "รายการเบิกสินค้าย่อย | Picking Item")
    public static class PickingItem {
        @Schema(description = "รหัสอะไหล่ | Part ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        private UUID partId;
        @Schema(description = "จำนวนที่ขอเบิก | Requested Quantity", example = "10")
        private int requestedQuantity;
        @Schema(description = "หมายเหตุ | Note", example = "เบิกเพื่อใช้งานในงาน Job-001")
        private String note;
    }
}
