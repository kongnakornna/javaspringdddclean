package com.icmon.module.weborder.presentation.dto.response;

import com.icmon.module.weborder.domain.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@Schema(description = "ข้อมูลติดตามออเดอร์ / Order Tracking Response")
public class OrderTrackingResponseDTO {

    @Schema(description = "รหัสออเดอร์ / Order ID")
    private UUID id;

    @Schema(description = "เลขที่ออเดอร์ / Order No", example = "WO-2026-0001")
    private String orderNo;

    @Schema(description = "สถานะปัจจุบัน / Current Status", example = "SHIPPED")
    private OrderStatus status;

    @Schema(description = "เลขพัสดุ / Tracking Number", example = "TH1234567890")
    private String trackingNumber;

    @Schema(description = "ผู้ให้บริการขนส่ง / Courier", example = "Kerry Express")
    private String courier;

    @Schema(description = "สถานะการชำระเงิน / Payment Status", example = "PAID")
    private String paymentStatus;
}
