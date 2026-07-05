package com.icmon.module.dashboard.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Schema(description = "ข้อมูลภาพรวมสินค้าคงคลัง / Inventory overview response")
public class InventoryOverviewResponseDTO {
    @Schema(description = "จำนวนอะไหล่ทั้งหมด / Total parts", example = "500")
    private Long totalParts;
    @Schema(description = "จำนวนรวมทั้งหมด / Total quantity", example = "12000")
    private Long totalQuantity;
    @Schema(description = "มูลค่ารวม / Total value", example = "1500000.00")
    private BigDecimal totalValue;
    @Schema(description = "จำนวนสินค้าคงคลังต่ำ / Low stock count", example = "20")
    private Long lowStockCount;
    @Schema(description = "จำนวนอะไหล่ที่ใช้งาน / Active parts", example = "450")
    private Long activeParts;
}
