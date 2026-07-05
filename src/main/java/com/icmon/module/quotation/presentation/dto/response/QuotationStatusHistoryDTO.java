package com.icmon.module.quotation.presentation.dto.response;

import com.icmon.module.quotation.domain.enums.QuotationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class QuotationStatusHistoryDTO {
    private UUID id;
    private UUID quotationId;
    private QuotationStatus fromStatus;
    private QuotationStatus toStatus;
    private UUID changedBy;
    private LocalDateTime changedAt;
    private String reason;
}
