package com.icmon.module.payment.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Schema(description = "ข้อมูลวิธีชำระเงิน - Payment Method Response")
public class PaymentMethodResponseDTO {
    @Schema(description = "รหัสวิธีชำระเงิน - Payment Method ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "รหัสวิธีการ - Method Code", example = "CREDIT_CARD")
    private String methodCode;

    @Schema(description = "ชื่อวิธีการ (ไทย) - Method Name (TH)", example = "บัตรเครดิต")
    private String methodName;

    @Schema(description = "ชื่อวิธีการ (อังกฤษ) - Method Name (EN)", example = "Credit Card")
    private String methodNameEn;

    @Schema(description = "สถานะใช้งาน - Is Active", example = "true")
    private boolean isActive;

    @Schema(description = "ต้องอนุมัติ - Requires Approval", example = "false")
    private boolean requiresApproval;

    @Schema(description = "ค่าธรรมเนียมร้อยละ - Fee Percentage", example = "2.50")
    private BigDecimal feePercentage;

    @Schema(description = "ค่าธรรมเนียมคงที่ - Fixed Fee", example = "10.00")
    private BigDecimal feeFixed;

    @Schema(description = "คำอธิบาย - Description", example = "ชำระผ่านบัตรเครดิต")
    private String description;
}
