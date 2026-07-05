package com.icmon.module.payment.presentation.dto.response;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PaymentHistoryResponseDTO {
    private UUID id;
    private UUID paymentId;
    private String fromStatus;
    private String toStatus;
    private UUID changedBy;
    private LocalDateTime changedAt;
    private String reason;
}
