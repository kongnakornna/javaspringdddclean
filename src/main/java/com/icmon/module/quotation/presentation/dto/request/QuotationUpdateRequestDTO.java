package com.icmon.module.quotation.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "คำขอแก้ไขใบเสนอราคา / Update Quotation Request")
public class QuotationUpdateRequestDTO {
    @Schema(description = "วันที่หมดอายุ / Expiry Date", example = "2025-12-31T23:59:59")
    private LocalDateTime expiryDate;

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

    @Schema(description = "หมายเหตุ / Notes", example = "ปรับราคาตามที่ตกลงกับลูกค้า")
    private String notes;

    @Schema(description = "ข้อกำหนดและเงื่อนไข / Terms and Conditions", example = "ชำระเงินภายใน 30 วัน")
    private String termsAndConditions;
}
