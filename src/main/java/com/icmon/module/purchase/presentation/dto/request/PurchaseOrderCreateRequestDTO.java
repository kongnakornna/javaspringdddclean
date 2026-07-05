package com.icmon.module.purchase.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "คำขอสร้างใบสั่งซื้อ / Create Purchase Order Request")
public class PurchaseOrderCreateRequestDTO {
    @Schema(description = "รหัสใบเสนอราคา / Quotation ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID quotationId;

    @Schema(description = "รหัสงาน / Job ID", example = "b2c3d4e5-f6a7-8901-bcde-f12345678901")
    private UUID jobId;

    @NotNull(message = "Supplier ID is required")
    @Schema(description = "รหัสผู้ขาย / Supplier ID", example = "c3d4e5f6-a7b8-9012-cdef-123456789012", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID supplierId;

    @Schema(description = "วันที่จัดส่งที่คาดหวัง / Expected Delivery Date", example = "2025-08-15T00:00:00")
    private LocalDateTime expectedDeliveryDate;

    @Schema(description = "อัตราภาษี / Tax Rate", example = "7.00")
    private BigDecimal taxRate;

    @Schema(description = "สกุลเงิน / Currency", example = "THB")
    private String currency;

    @Schema(description = "อัตราแลกเปลี่ยน / Exchange Rate", example = "1.00")
    private BigDecimal exchangeRate;

    @Schema(description = "เงื่อนไขการชำระเงิน / Payment Terms", example = "เครดิต 30 วัน")
    private String paymentTerms;

    @Schema(description = "ที่อยู่จัดส่ง / Delivery Address", example = "เลขที่ 1 ถนนสุขุมวิท แขวงคลองเตย เขตคลองเตย กรุงเทพฯ 10110")
    private String deliveryAddress;

    @Schema(description = "หมายเหตุ / Notes", example = "จัดส่งด่วน")
    private String notes;

    @Schema(description = "ข้อกำหนดและเงื่อนไข / Terms and Conditions", example = "สินค้าต้องผ่านการตรวจสอบคุณภาพก่อนจัดส่ง")
    private String termsAndConditions;
}
