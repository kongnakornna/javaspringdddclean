package com.icmon.module.i18n.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "คำขอสลับภาษา / Language switch request")
public class LanguageSwitchRequestDTO {
    @NotBlank(message = "Language code is required")
    @Schema(description = "รหัสภาษา / Language code", example = "en")
    private String languageCode;
}
