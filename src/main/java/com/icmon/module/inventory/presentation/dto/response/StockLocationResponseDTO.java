package com.icmon.module.inventory.presentation.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class StockLocationResponseDTO {
    private UUID id;
    private String locationCode;
    private String locationName;
    private String locationType;
    private String zone;
    private Integer capacity;
    private Integer currentUsage;
    private boolean isActive;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
