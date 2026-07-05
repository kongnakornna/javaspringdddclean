package com.icmon.module.inventory.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Schema(description = "คำขอทำรายการเคลื่อนไหวสินค้าคงคลัง | Inventory Transaction Request")
public class InventoryTransactionRequestDTO {
    @Schema(description = "รหัสอะไหล่ | Part ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID partId;
    @Schema(description = "ประเภทธุรกรรม | Transaction Type", example = "RECEIVE")
    private String transactionType;
    @Schema(description = "ประเภทเอกสารอ้างอิง | Reference Type", example = "PURCHASE_ORDER")
    private String referenceType;
    @Schema(description = "รหัสเอกสารอ้างอิง | Reference ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID referenceId;
    @Schema(description = "จำนวน | Quantity", example = "10")
    private int quantity;
    @Schema(description = "ต้นทุนต่อหน่วย | Unit Cost", example = "500.00")
    private BigDecimal unitCost;
    @Schema(description = "หมายเหตุ | Note", example = "รับสินค้าตามใบสั่งซื้อ PO-2024-001")
    private String note;
    @Schema(description = "รหัสผู้ดำเนินการ | Performed By", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID performedBy;
}
