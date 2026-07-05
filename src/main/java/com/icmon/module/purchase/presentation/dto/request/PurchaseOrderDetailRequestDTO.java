package com.icmon.module.purchase.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Schema(description = "คำขอรายการในใบสั่งซื้อ / Purchase Order Detail Request")
public class PurchaseOrderDetailRequestDTO {
    @NotNull(message = "Part ID is required")
    @Schema(description = "รหัสอะไหล่ / Part ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID partId;

    @NotNull(message = "Quantity ordered is required")
    @Schema(description = "จำนวนที่สั่งซื้อ / Quantity Ordered", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantityOrdered;

    @NotNull(message = "Unit price is required")
    @Schema(description = "ราคาต่อหน่วย / Unit Price", example = "350.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal unitPrice;

    @Schema(description = "ส่วนลด / Discount", example = "100.00")
    private BigDecimal discount;

    @Schema(description = "หมายเหตุ / Note", example = "สั่งเพิ่มเติมจากรอบก่อน")
    private String note;
}
