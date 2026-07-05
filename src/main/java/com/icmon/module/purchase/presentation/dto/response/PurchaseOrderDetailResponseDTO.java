package com.icmon.module.purchase.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ข้อมูลรายการในใบสั่งซื้อ / Purchase Order Detail Response")
public class PurchaseOrderDetailResponseDTO {
    @Schema(description = "รหัสรายการ / ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID id;

    @Schema(description = "รหัสหัวใบสั่งซื้อ / PO Header ID", example = "b2c3d4e5-f6a7-8901-bcde-f12345678901")
    private UUID poHeaderId;

    @Schema(description = "รหัสอะไหล่ / Part ID", example = "c3d4e5f6-a7b8-9012-cdef-123456789012")
    private UUID partId;

    @Schema(description = "จำนวนที่สั่งซื้อ / Quantity Ordered", example = "10")
    private Integer quantityOrdered;

    @Schema(description = "จำนวนที่รับแล้ว / Quantity Received", example = "5")
    private Integer quantityReceived;

    @Schema(description = "ราคาต่อหน่วย / Unit Price", example = "350.00")
    private BigDecimal unitPrice;

    @Schema(description = "ราคารวม / Total Price", example = "3500.00")
    private BigDecimal totalPrice;

    @Schema(description = "ส่วนลด / Discount", example = "100.00")
    private BigDecimal discount;

    @Schema(description = "ราคาสุทธิ / Net Price", example = "3400.00")
    private BigDecimal netPrice;

    @Schema(description = "หมายเหตุ / Note", example = "อะไหล่แท้จากศูนย์บริการ")
    private String note;
}
