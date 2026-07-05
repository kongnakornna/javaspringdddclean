package com.icmon.module.payment.presentation.dto.request;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PaymentSearchRequestDTO {
    private UUID customerId;
    private String status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
