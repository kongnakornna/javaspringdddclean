package com.icmon.module.weborder.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "คำขอเพิ่มสินค้าในตะกร้า / Add to Cart Request")
public class AddToCartRequestDTO {

    @NotNull(message = "Item ID is required")
    @Schema(description = "รหัสสินค้า / Item ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID itemId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Schema(description = "จำนวน / Quantity", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantity;

    @Schema(description = "ตัวเลือกเพิ่มเติม (JSON) / Additional attributes", example = "{\"color\":\"red\"}")
    private String attributes;
}
