package com.icmon.module.inventory.presentation.dto.response;

import com.icmon.module.inventory.infrastructure.entity.StockLocationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockLocationResponseDTO {
    private UUID id;
    private String locationCode;
    private String locationName;
    private String locationType;
    private String zone;
    private Integer capacity;
    private Integer currentUsage;
    private Boolean isActive;

    public static StockLocationResponseDTO fromEntity(StockLocationEntity entity) {
        return StockLocationResponseDTO.builder()
                .id(entity.getId()).locationCode(entity.getLocationCode())
                .locationName(entity.getLocationName()).locationType(entity.getLocationType())
                .zone(entity.getZone()).capacity(entity.getCapacity())
                .currentUsage(entity.getCurrentUsage()).isActive(entity.getIsActive())
                .build();
    }
}
