package com.icmon.module.quotation.presentation.dto.response;

import com.icmon.module.quotation.domain.enums.QuotationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Schema(description = "ประวัติสถานะใบเสนอราคา / Quotation Status History")
public class QuotationStatusHistoryDTO {
    @Schema(description = "รหัสประวัติ / ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID id;

    @Schema(description = "รหัสใบเสนอราคา / Quotation ID", example = "b2c3d4e5-f6a7-8901-bcde-f12345678901")
    private UUID quotationId;

    @Schema(description = "สถานะเดิม / From Status", example = "DRAFT")
    private QuotationStatus fromStatus;

    @Schema(description = "สถานะใหม่ / To Status", example = "APPROVED")
    private QuotationStatus toStatus;

    @Schema(description = "ผู้เปลี่ยนแปลง / Changed By", example = "c3d4e5f6-a7b8-9012-cdef-123456789012")
    private UUID changedBy;

    @Schema(description = "วันที่เปลี่ยนแปลง / Changed At", example = "2025-07-02T14:30:00")
    private LocalDateTime changedAt;

    @Schema(description = "เหตุผล / Reason", example = "อนุมัติโดยผู้จัดการ")
    private String reason;
}
