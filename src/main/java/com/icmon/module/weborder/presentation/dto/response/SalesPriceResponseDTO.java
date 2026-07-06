package com.icmon.module.weborder.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Schema(description = "ข้อมูลราคาขาย / Sales Price Response")
public class SalesPriceResponseDTO {

    @Schema(description = "รหัส / ID")
    private UUID id;

    @Schema(description = "รหัสสินค้า / Item ID")
    private UUID itemId;

    @Schema(description = "ระดับราคา / Price Tier", example = "DEFAULT")
    private String priceTier;

    @Schema(description = "ราคาต่อหน่วย / Unit Price", example = "500.00")
    private BigDecimal unitPrice;

    @Schema(description = "สกุลเงิน / Currency", example = "THB")
    private String currency;

    @Schema(description = "วันที่มีผล / Effective Date")
    private LocalDateTime effectiveDate;

    @Schema(description = "วันหมดอายุ / Expiry Date")
    private LocalDateTime expiryDate;

    @Schema(description = "จำนวนขั้นต่ำ / Min Quantity", example = "1")
    private Integer minQuantity;

    @Schema(description = "ใช้งานอยู่ / Active", example = "true")
    private Boolean isActive;
}
