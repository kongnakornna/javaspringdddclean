package com.icmon.module.weborder.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "คำขอปรับจำนวนสินค้าในตะกร้า / Update Cart Request")
public class UpdateCartRequestDTO {

    @NotNull(message = "Item ID is required")
    @Schema(description = "รหัสสินค้า / Item ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID itemId;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity must be 0 or more (0 to remove)")
    @Schema(description = "จำนวนใหม่ / New quantity", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantity;
}
