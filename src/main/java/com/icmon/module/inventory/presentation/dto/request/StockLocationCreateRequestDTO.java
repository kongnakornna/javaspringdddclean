package com.icmon.module.inventory.presentation.dto.request;

import lombok.Data;

@Data
public class StockLocationCreateRequestDTO {
    private String locationCode;
    private String locationName;
    private String locationType;
    private String zone;
    private Integer capacity;
    private String notes;
}
