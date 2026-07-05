package com.icmon.module.quotation.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Schema(description = "คำขอรายการอะไหล่ในใบเสนอราคา / Quotation Part Request")
public class QuotationPartRequestDTO {
    @NotNull(message = "Quotation ID is required")
    @Schema(description = "รหัสใบเสนอราคา / Quotation ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID quotationId;

    @NotNull(message = "Part ID is required")
    @Schema(description = "รหัสอะไหล่ / Part ID", example = "d4e5f6a7-b8c9-0123-defa-234567890123", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID partId;

    @NotNull(message = "Quantity is required")
    @Schema(description = "จำนวน / Quantity", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantity;

    @NotNull(message = "Unit price is required")
    @Schema(description = "ราคาต่อหน่วย / Unit Price", example = "350.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal unitPrice;

    @Schema(description = "ส่วนลด / Discount", example = "50.00")
    private BigDecimal discount;

    @Schema(description = "หมายเหตุ / Note", example = "อะไหล่แท้จากศูนย์บริการ")
    private String note;
}
