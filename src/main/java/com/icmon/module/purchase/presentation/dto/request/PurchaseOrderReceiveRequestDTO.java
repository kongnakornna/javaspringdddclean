package com.icmon.module.purchase.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "คำขอยืนยันรับสินค้า / Receive Purchase Order Request")
public class PurchaseOrderReceiveRequestDTO {
    @Schema(description = "วันที่รับสินค้าจริง / Actual Delivery Date", example = "2025-08-10T15:30:00")
    private LocalDateTime actualDeliveryDate;

    @Schema(description = "หมายเหตุ / Notes", example = "รับสินค้าครบตามจำนวนที่สั่ง")
    private String notes;
}
