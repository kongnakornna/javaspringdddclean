package com.icmon.module.weborder.presentation.dto.response;

import com.icmon.module.weborder.domain.enums.OrderSource;
import com.icmon.module.weborder.domain.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Schema(description = "ข้อมูลออเดอร์ / Order Response")
public class OrderResponseDTO {

    @Schema(description = "รหัสออเดอร์ / ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID id;

    @Schema(description = "เลขที่ออเดอร์ / Order No", example = "WO-2026-0001")
    private String orderNo;

    @Schema(description = "รหัสลูกค้า / Customer ID")
    private UUID customerId;

    @Schema(description = "วันที่สั่งซื้อ / Order Date")
    private LocalDateTime orderDate;

    @Schema(description = "สถานะ / Status", example = "PENDING")
    private OrderStatus status;

    @Schema(description = "สถานะการชำระเงิน / Payment Status", example = "PENDING")
    private String paymentStatus;

    @Schema(description = "ยอดรวมสุทธิ / Total", example = "1548.00")
    private BigDecimal total;

    @Schema(description = "วันที่สร้าง / Created At")
    private LocalDateTime createdAt;
}
