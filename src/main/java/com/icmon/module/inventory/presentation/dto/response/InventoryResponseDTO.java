package com.icmon.module.inventory.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "ข้อมูลธุรกรรมสินค้าคงคลัง | Inventory Transaction Response")
public class InventoryResponseDTO {
    @Schema(description = "รหัส | ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;
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
    @Schema(description = "จำนวนก่อนหน้า | Previous Quantity", example = "40")
    private int previousQuantity;
    @Schema(description = "จำนวนใหม่ | New Quantity", example = "50")
    private int newQuantity;
    @Schema(description = "ต้นทุนต่อหน่วย | Unit Cost", example = "500.00")
    private BigDecimal unitCost;
    @Schema(description = "ต้นทุนรวม | Total Cost", example = "5000.00")
    private BigDecimal totalCost;
    @Schema(description = "วันที่ทำรายการ | Transaction Date", example = "2024-06-15T10:30:00")
    private LocalDateTime transactionDate;
    @Schema(description = "หมายเหตุ | Note", example = "รับสินค้าตามใบสั่งซื้อ PO-2024-001")
    private String note;
    @Schema(description = "รหัสผู้ดำเนินการ | Performed By", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID performedBy;
    @Schema(description = "วันที่สร้าง | Created At", example = "2024-06-15T10:30:00")
    private LocalDateTime createdAt;
}
