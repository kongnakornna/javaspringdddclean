package com.icmon.module.purchase.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderStatusHistoryDTO {
    private UUID id;
    private UUID poHeaderId;
    private String fromStatus;
    private String toStatus;
    private UUID changedBy;
    private LocalDateTime changedAt;
    private String reason;
}
