package com.icmon.module.weborder.presentation.dto.request;

import com.icmon.module.weborder.domain.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "คำขอเปลี่ยนสถานะออเดอร์ / Update Order Status Request")
public class OrderStatusUpdateRequestDTO {

    @NotNull(message = "Status is required")
    @Schema(description = "สถานะใหม่ / New Status", example = "CONFIRMED", requiredMode = Schema.RequiredMode.REQUIRED)
    private OrderStatus status;

    @Schema(description = "เหตุผล / Reason", example = "ยืนยันออเดอร์โดยแอดมิน")
    private String reason;

    @Schema(description = "เลขพัสดุ / Tracking Number", example = "TH1234567890")
    private String trackingNumber;

    @Schema(description = "ผู้ให้บริการขนส่ง / Courier", example = "Kerry Express")
    private String courier;
}
