package com.icmon.module.quotation.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Schema(description = "คำขอรายการบริการในใบเสนอราคา / Quotation Service Request")
public class QuotationServiceRequestDTO {
    @NotNull(message = "Quotation ID is required")
    @Schema(description = "รหัสใบเสนอราคา / Quotation ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID quotationId;

    @NotNull(message = "Service ID is required")
    @Schema(description = "รหัสบริการ / Service ID", example = "c3d4e5f6-a7b8-9012-cdef-123456789012", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID serviceId;

    @NotNull(message = "Quantity is required")
    @Schema(description = "จำนวน / Quantity", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantity;

    @NotNull(message = "Unit price is required")
    @Schema(description = "ราคาต่อหน่วย / Unit Price", example = "1500.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal unitPrice;

    @Schema(description = "ส่วนลด / Discount", example = "200.00")
    private BigDecimal discount;

    @Schema(description = "หมายเหตุ / Note", example = "บริการติดตั้งระบบปรับอากาศ")
    private String note;
}
