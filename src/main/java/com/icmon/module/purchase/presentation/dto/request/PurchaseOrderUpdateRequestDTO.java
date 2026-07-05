package com.icmon.module.purchase.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "คำขอแก้ไขใบสั่งซื้อ / Update Purchase Order Request")
public class PurchaseOrderUpdateRequestDTO {
    @Schema(description = "รหัสผู้ขาย / Supplier ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID supplierId;

    @Schema(description = "วันที่จัดส่งที่คาดหวัง / Expected Delivery Date", example = "2025-08-15T00:00:00")
    private LocalDateTime expectedDeliveryDate;

    @Schema(description = "อัตราภาษี / Tax Rate", example = "7.00")
    private BigDecimal taxRate;

    @Schema(description = "ประเภทส่วนลด / Discount Type", example = "PERCENTAGE")
    private String discountType;

    @Schema(description = "มูลค่าส่วนลด / Discount Value", example = "500.00")
    private BigDecimal discountValue;

    @Schema(description = "สกุลเงิน / Currency", example = "THB")
    private String currency;

    @Schema(description = "อัตราแลกเปลี่ยน / Exchange Rate", example = "1.00")
    private BigDecimal exchangeRate;

    @Schema(description = "ค่าขนส่ง / Shipping Cost", example = "300.00")
    private BigDecimal shippingCost;

    @Schema(description = "เงื่อนไขการชำระเงิน / Payment Terms", example = "เครดิต 30 วัน")
    private String paymentTerms;

    @Schema(description = "ที่อยู่จัดส่ง / Delivery Address", example = "เลขที่ 1 ถนนสุขุมวิท แขวงคลองเตย เขตคลองเตย กรุงเทพฯ 10110")
    private String deliveryAddress;

    @Schema(description = "หมายเหตุ / Notes", example = "เปลี่ยนที่อยู่จัดส่ง")
    private String notes;

    @Schema(description = "ข้อกำหนดและเงื่อนไข / Terms and Conditions", example = "สินค้าต้องผ่านการตรวจสอบคุณภาพก่อนจัดส่ง")
    private String termsAndConditions;
}
