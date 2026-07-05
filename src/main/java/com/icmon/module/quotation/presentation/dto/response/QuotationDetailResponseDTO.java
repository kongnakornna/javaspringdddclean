package com.icmon.module.quotation.presentation.dto.response;

import com.icmon.module.quotation.domain.enums.QuotationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Schema(description = "รายละเอียดใบเสนอราคา / Quotation Detail Response")
public class QuotationDetailResponseDTO {
    @Schema(description = "รหัสใบเสนอราคา / ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID id;

    @Schema(description = "เลขที่ใบเสนอราคา / Quotation Number", example = "QT-2025-0001")
    private String quotationNo;

    @Schema(description = "รหัสงาน / Job ID", example = "b2c3d4e5-f6a7-8901-bcde-f12345678901")
    private UUID jobId;

    @Schema(description = "รหัสลูกค้า / Customer ID", example = "c3d4e5f6-a7b8-9012-cdef-123456789012")
    private UUID customerId;

    @Schema(description = "วันที่ออกใบเสนอราคา / Quotation Date", example = "2025-07-01T10:30:00")
    private LocalDateTime quotationDate;

    @Schema(description = "วันที่หมดอายุ / Expiry Date", example = "2025-07-31T23:59:59")
    private LocalDateTime expiryDate;

    @Schema(description = "สถานะ / Status", example = "APPROVED")
    private QuotationStatus status;

    @Schema(description = "ยอดรวมก่อนภาษี / Subtotal", example = "15000.00")
    private BigDecimal subtotal;

    @Schema(description = "อัตราภาษี / Tax Rate", example = "7.00")
    private BigDecimal taxRate;

    @Schema(description = "จำนวนภาษี / Tax Amount", example = "1050.00")
    private BigDecimal taxAmount;

    @Schema(description = "ประเภทส่วนลด / Discount Type", example = "PERCENTAGE")
    private String discountType;

    @Schema(description = "มูลค่าส่วนลด / Discount Value", example = "500.00")
    private BigDecimal discountValue;

    @Schema(description = "ยอดรวมสุทธิ / Total", example = "15550.00")
    private BigDecimal total;

    @Schema(description = "จำนวนเงินเป็นตัวอักษร (ไทย) / Amount in Words (Thai)", example = "หนึ่งหมื่นห้าพันห้าร้อยห้าสิบบาทถ้วน")
    private String amountInWordsTh;

    @Schema(description = "จำนวนเงินเป็นตัวอักษร (อังกฤษ) / Amount in Words (English)", example = "Fifteen Thousand Five Hundred Fifty Baht Only")
    private String amountInWordsEn;

    @Schema(description = "สกุลเงิน / Currency", example = "THB")
    private String currency;

    @Schema(description = "อัตราแลกเปลี่ยน / Exchange Rate", example = "1.00")
    private BigDecimal exchangeRate;

    @Schema(description = "หมายเหตุ / Notes", example = "จัดส่งภายใน 7 วันทำการ")
    private String notes;

    @Schema(description = "ข้อกำหนดและเงื่อนไข / Terms and Conditions", example = "ชำระเงินภายใน 30 วัน")
    private String termsAndConditions;

    @Schema(description = "ผู้อนุมัติ / Approved By", example = "e5f6a7b8-c9d0-1234-efab-345678901234")
    private UUID approvedBy;

    @Schema(description = "วันที่อนุมัติ / Approved At", example = "2025-07-02T14:30:00")
    private LocalDateTime approvedAt;

    @Schema(description = "เหตุผลที่ปฏิเสธ / Rejected Reason", example = "ราคาสูงเกินไป")
    private String rejectedReason;

    @Schema(description = "แปลงเป็นใบสั่งซื้อแล้ว / Converted To PO", example = "true")
    private Boolean convertedToPo;

    @Schema(description = "วันที่สร้าง / Created At", example = "2025-07-01T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "วันที่อัปเดตล่าสุด / Updated At", example = "2025-07-02T14:30:00")
    private LocalDateTime updatedAt;
}
