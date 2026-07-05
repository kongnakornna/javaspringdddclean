package com.icmon.module.inventory.presentation.dto.request;

import lombok.Data;

@Data
public class StockLocationUpdateRequestDTO {
    private String locationName;
    private String locationType;
    private String zone;
    private Integer capacity;
    private Boolean isActive;
    private String notes;
}
