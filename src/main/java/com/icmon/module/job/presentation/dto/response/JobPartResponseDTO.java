package com.icmon.module.job.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "ข้อมูลอะไหล่ในงาน - Job part response")
@Data
@Builder
public class JobPartResponseDTO {
    @Schema(description = "รหัส - ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;
    @Schema(description = "รหัสงาน - Job ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID jobId;
    @Schema(description = "รหัสอะไหล่ - Part ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID partId;
    @Schema(description = "จำนวน - Quantity", example = "2")
    private Integer quantity;
    @Schema(description = "ราคาต่อหน่วย - Unit price", example = "500.00")
    private BigDecimal unitPrice;
    @Schema(description = "ราคารวม - Total price", example = "1000.00")
    private BigDecimal totalPrice;
    @Schema(description = "ส่วนลด - Discount", example = "0.00")
    private BigDecimal discount;
    @Schema(description = "ราคาสุทธิ - Net price", example = "1000.00")
    private BigDecimal netPrice;
    @Schema(description = "หมายเหตุ - Note", example = "ไส้กรองเครื่องแท้")
    private String note;
}
