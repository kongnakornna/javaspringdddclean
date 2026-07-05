package com.icmon.module.job.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "ข้อมูลบริการในงาน - Job service response")
@Data
@Builder
public class JobServiceResponseDTO {
    @Schema(description = "รหัส - ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;
    @Schema(description = "รหัสงาน - Job ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID jobId;
    @Schema(description = "รหัสบริการ - Service ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID serviceId;
    @Schema(description = "จำนวน - Quantity", example = "1")
    private Integer quantity;
    @Schema(description = "ราคาต่อหน่วย - Unit price", example = "1500.00")
    private BigDecimal unitPrice;
    @Schema(description = "ราคารวม - Total price", example = "1500.00")
    private BigDecimal totalPrice;
    @Schema(description = "ส่วนลด - Discount", example = "100.00")
    private BigDecimal discount;
    @Schema(description = "ราคาสุทธิ - Net price", example = "1400.00")
    private BigDecimal netPrice;
    @Schema(description = "หมายเหตุ - Note", example = "ใช้น้ำยี่ห้อ Mobil")
    private String note;
}
