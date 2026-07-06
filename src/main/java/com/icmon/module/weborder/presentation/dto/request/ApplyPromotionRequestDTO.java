package com.icmon.module.weborder.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "คำขอใช้โค้ดส่วนลด / Apply Promotion Request")
public class ApplyPromotionRequestDTO {

    @NotBlank(message = "Promotion code is required")
    @Schema(description = "โค้ดส่วนลด / Promotion Code", example = "WELCOME10", requiredMode = Schema.RequiredMode.REQUIRED)
    private String promotionCode;
}
