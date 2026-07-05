package com.icmon.module.payment.presentation.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class RefundRequestDTO {
    private BigDecimal amount;
    private String reason;
}
