package com.icmon.module.purchase.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "ประวัติสถานะใบสั่งซื้อ / Purchase Order Status History")
public class PurchaseOrderStatusHistoryDTO {
    @Schema(description = "รหัสประวัติ / ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID id;

    @Schema(description = "รหัสหัวใบสั่งซื้อ / PO Header ID", example = "b2c3d4e5-f6a7-8901-bcde-f12345678901")
    private UUID poHeaderId;

    @Schema(description = "สถานะเดิม / From Status", example = "DRAFT")
    private String fromStatus;

    @Schema(description = "สถานะใหม่ / To Status", example = "SENT")
    private String toStatus;

    @Schema(description = "ผู้เปลี่ยนแปลง / Changed By", example = "c3d4e5f6-a7b8-9012-cdef-123456789012")
    private UUID changedBy;

    @Schema(description = "วันที่เปลี่ยนแปลง / Changed At", example = "2025-07-05T09:30:00")
    private LocalDateTime changedAt;

    @Schema(description = "เหตุผล / Reason", example = "ส่งใบสั่งซื้อให้ผู้ขายแล้ว")
    private String reason;
}
