package com.icmon.module.payment.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Schema(description = "ยอดค้างชำระ - Outstanding Balance Response")
public class OutstandingBalanceResponseDTO {
    @Schema(description = "รหัสลูกค้า - Customer ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID customerId;

    @Schema(description = "ยอดค้างชำระรวม - Total Outstanding", example = "5000.00")
    private BigDecimal totalOutstanding;

    @Schema(description = "จำนวนใบแจ้งหนี้ที่ยังไม่ชำระ - Unpaid Invoice Count", example = "3")
    private int unpaidInvoiceCount;
}
