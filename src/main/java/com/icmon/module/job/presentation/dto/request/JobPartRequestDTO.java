package com.icmon.module.job.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "คำขอเพิ่มอะไหล่ในงาน - Job part request")
@Data
public class JobPartRequestDTO {

    @NotNull(message = "Part ID is required")
    @Schema(description = "รหัสอะไหล่ - Part ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID partId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Schema(description = "จำนวน - Quantity", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantity;

    @NotNull(message = "Unit price is required")
    @Schema(description = "ราคาต่อหน่วย - Unit price", example = "500.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal unitPrice;

    @Schema(description = "ส่วนลด - Discount", example = "0.00")
    private BigDecimal discount;

    @Schema(description = "หมายเหตุ - Note", example = "ไส้กรองเครื่องแท้")
    private String note;
}
