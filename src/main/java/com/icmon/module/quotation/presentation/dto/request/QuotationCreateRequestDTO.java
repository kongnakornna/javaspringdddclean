package com.icmon.module.quotation.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "คำขอสร้างใบเสนอราคา / Create Quotation Request")
public class QuotationCreateRequestDTO {
    @NotNull(message = "Job ID is required")
    @Schema(description = "รหัสงาน / Job ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID jobId;

    @NotNull(message = "Customer ID is required")
    @Schema(description = "รหัสลูกค้า / Customer ID", example = "b2c3d4e5-f6a7-8901-bcde-f12345678901", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID customerId;

    @Schema(description = "วันที่หมดอายุ / Expiry Date", example = "2025-12-31T23:59:59")
    private LocalDateTime expiryDate;

    @Schema(description = "อัตราภาษี / Tax Rate", example = "7.00")
    private BigDecimal taxRate;

    @Schema(description = "สกุลเงิน / Currency", example = "THB")
    private String currency;

    @Schema(description = "อัตราแลกเปลี่ยน / Exchange Rate", example = "1.00")
    private BigDecimal exchangeRate;

    @Schema(description = "หมายเหตุ / Notes", example = "จัดส่งภายใน 7 วันทำการ")
    private String notes;

    @Schema(description = "ข้อกำหนดและเงื่อนไข / Terms and Conditions", example = "ชำระเงินภายใน 30 วัน")
    private String termsAndConditions;
}
