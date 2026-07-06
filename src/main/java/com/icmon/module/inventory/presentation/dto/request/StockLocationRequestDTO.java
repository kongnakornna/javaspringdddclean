package com.icmon.module.inventory.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StockLocationRequestDTO {
    @NotBlank @Size(max = 20)
    private String locationCode;
    @NotBlank @Size(max = 100)
    private String locationName;
    private String locationType;
    private String zone;
    private Integer capacity;
}
