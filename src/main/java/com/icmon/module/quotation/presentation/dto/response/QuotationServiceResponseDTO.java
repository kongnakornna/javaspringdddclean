package com.icmon.module.quotation.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@Schema(description = "ข้อมูลรายการบริการในใบเสนอราคา / Quotation Service Response")
public class QuotationServiceResponseDTO {
    @Schema(description = "รหัสรายการ / ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID id;

    @Schema(description = "รหัสใบเสนอราคา / Quotation ID", example = "b2c3d4e5-f6a7-8901-bcde-f12345678901")
    private UUID quotationId;

    @Schema(description = "รหัสบริการ / Service ID", example = "c3d4e5f6-a7b8-9012-cdef-123456789012")
    private UUID serviceId;

    @Schema(description = "จำนวน / Quantity", example = "2")
    private Integer quantity;

    @Schema(description = "ราคาต่อหน่วย / Unit Price", example = "1500.00")
    private BigDecimal unitPrice;

    @Schema(description = "ราคารวม / Total Price", example = "3000.00")
    private BigDecimal totalPrice;

    @Schema(description = "ส่วนลด / Discount", example = "200.00")
    private BigDecimal discount;

    @Schema(description = "ราคาสุทธิ / Net Price", example = "2800.00")
    private BigDecimal netPrice;

    @Schema(description = "หมายเหตุ / Note", example = "บริการติดตั้งระบบปรับอากาศ")
    private String note;
}
