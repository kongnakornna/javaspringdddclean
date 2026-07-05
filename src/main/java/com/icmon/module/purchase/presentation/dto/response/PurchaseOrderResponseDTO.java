package com.icmon.module.purchase.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ข้อมูลใบสั่งซื้อ / Purchase Order Response")
public class PurchaseOrderResponseDTO {
    @Schema(description = "รหัสใบสั่งซื้อ / ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID id;

    @Schema(description = "เลขที่ใบสั่งซื้อ / PO Number", example = "PO-2025-0001")
    private String poNo;

    @Schema(description = "รหัสใบเสนอราคา / Quotation ID", example = "b2c3d4e5-f6a7-8901-bcde-f12345678901")
    private UUID quotationId;

    @Schema(description = "รหัสงาน / Job ID", example = "c3d4e5f6-a7b8-9012-cdef-123456789012")
    private UUID jobId;

    @Schema(description = "รหัสผู้ขาย / Supplier ID", example = "d4e5f6a7-b8c9-0123-defa-234567890123")
    private UUID supplierId;

    @Schema(description = "วันที่ออกใบสั่งซื้อ / PO Date", example = "2025-07-05T09:00:00")
    private LocalDateTime poDate;

    @Schema(description = "วันที่จัดส่งที่คาดหวัง / Expected Delivery Date", example = "2025-08-15T00:00:00")
    private LocalDateTime expectedDeliveryDate;

    @Schema(description = "วันที่รับสินค้าจริง / Actual Delivery Date", example = "2025-08-10T15:30:00")
    private LocalDateTime actualDeliveryDate;

    @Schema(description = "สถานะ / Status", example = "SENT")
    private String status;

    @Schema(description = "ยอดรวมก่อนภาษี / Subtotal", example = "35000.00")
    private BigDecimal subtotal;

    @Schema(description = "อัตราภาษี / Tax Rate", example = "7.00")
    private BigDecimal taxRate;

    @Schema(description = "จำนวนภาษี / Tax Amount", example = "2450.00")
    private BigDecimal taxAmount;

    @Schema(description = "ประเภทส่วนลด / Discount Type", example = "PERCENTAGE")
    private String discountType;

    @Schema(description = "มูลค่าส่วนลด / Discount Value", example = "1000.00")
    private BigDecimal discountValue;

    @Schema(description = "ยอดรวมสุทธิ / Total", example = "36450.00")
    private BigDecimal total;

    @Schema(description = "สกุลเงิน / Currency", example = "THB")
    private String currency;

    @Schema(description = "อัตราแลกเปลี่ยน / Exchange Rate", example = "1.00")
    private BigDecimal exchangeRate;

    @Schema(description = "ค่าขนส่ง / Shipping Cost", example = "500.00")
    private BigDecimal shippingCost;

    @Schema(description = "เงื่อนไขการชำระเงิน / Payment Terms", example = "เครดิต 30 วัน")
    private String paymentTerms;

    @Schema(description = "ที่อยู่จัดส่ง / Delivery Address", example = "เลขที่ 1 ถนนสุขุมวิท แขวงคลองเตย เขตคลองเตย กรุงเทพฯ 10110")
    private String deliveryAddress;

    @Schema(description = "หมายเหตุ / Notes", example = "จัดส่งด่วน")
    private String notes;

    @Schema(description = "ข้อกำหนดและเงื่อนไข / Terms and Conditions", example = "สินค้าต้องผ่านการตรวจสอบคุณภาพก่อนจัดส่ง")
    private String termsAndConditions;

    @Schema(description = "วันที่ส่ง / Sent At", example = "2025-07-05T09:30:00")
    private LocalDateTime sentAt;

    @Schema(description = "วันที่ยืนยัน / Confirmed At", example = "2025-07-06T10:00:00")
    private LocalDateTime confirmedAt;

    @Schema(description = "ผู้รับสินค้า / Received By", example = "e5f6a7b8-c9d0-1234-efab-345678901234")
    private UUID receivedBy;

    @Schema(description = "วันที่สร้าง / Created At", example = "2025-07-05T09:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "วันที่อัปเดตล่าสุด / Updated At", example = "2025-07-06T10:00:00")
    private LocalDateTime updatedAt;
}
