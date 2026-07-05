package com.icmon.module.payment.presentation.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OutstandingBalanceResponseDTO {
    private UUID customerId;
    private BigDecimal totalOutstanding;
    private int unpaidInvoiceCount;
}
