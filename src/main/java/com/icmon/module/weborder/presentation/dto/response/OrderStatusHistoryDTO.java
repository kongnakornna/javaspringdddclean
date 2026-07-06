package com.icmon.module.weborder.presentation.dto.response;

import com.icmon.module.weborder.domain.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Schema(description = "ประวัติสถานะออเดอร์ / Order Status History")
public class OrderStatusHistoryDTO {

    @Schema(description = "รหัสประวัติ / ID")
    private UUID id;

    @Schema(description = "รหัสออเดอร์ / Order ID")
    private UUID orderId;

    @Schema(description = "สถานะเดิม / From Status", example = "PENDING")
    private OrderStatus fromStatus;

    @Schema(description = "สถานะใหม่ / To Status", example = "CONFIRMED")
    private OrderStatus toStatus;

    @Schema(description = "ผู้เปลี่ยนแปลง / Changed By")
    private UUID changedBy;

    @Schema(description = "วันที่เปลี่ยนแปลง / Changed At")
    private LocalDateTime changedAt;

    @Schema(description = "เหตุผล / Reason", example = "ยืนยันออเดอร์โดยระบบ")
    private String reason;
}
