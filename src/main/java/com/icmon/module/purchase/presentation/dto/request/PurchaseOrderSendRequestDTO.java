package com.icmon.module.purchase.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "คำขอส่งใบสั่งซื้อ / Send Purchase Order Request")
public class PurchaseOrderSendRequestDTO {
    @Schema(description = "ส่งอีเมลหรือไม่ / Send Email", example = "true")
    private Boolean sendEmail;

    @Schema(description = "อีเมลผู้ขาย / Supplier Email", example = "supplier@company.com")
    private String supplierEmail;
}
