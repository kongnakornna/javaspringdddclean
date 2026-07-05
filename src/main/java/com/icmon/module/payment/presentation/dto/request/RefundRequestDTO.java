package com.icmon.module.payment.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Schema(description = "คำขอคืนเงิน - Refund Request")
public class RefundRequestDTO {
    @Schema(description = "จำนวนเงินคืน - Refund Amount", example = "500.00")
    private BigDecimal amount;

    @Schema(description = "เหตุผลในการคืนเงิน - Refund Reason", example = "ชำระเงินเกิน")
    private String reason;
}
