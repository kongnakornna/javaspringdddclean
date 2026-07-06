package com.icmon.module.inventory.presentation.dto.response;

import com.icmon.module.inventory.infrastructure.entity.StockTakeHeaderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockTakeResponseDTO {
    private UUID id;
    private String stocktakeNo;
    private LocalDateTime stocktakeDate;
    private String status;
    private UUID startedBy;
    private LocalDateTime startedAt;
    private UUID completedBy;
    private LocalDateTime completedAt;
    private String notes;

    public static StockTakeResponseDTO fromEntity(StockTakeHeaderEntity entity) {
        return StockTakeResponseDTO.builder()
                .id(entity.getId()).stocktakeNo(entity.getStocktakeNo())
                .stocktakeDate(entity.getStocktakeDate()).status(entity.getStatus())
                .startedBy(entity.getStartedBy()).startedAt(entity.getStartedAt())
                .completedBy(entity.getCompletedBy()).completedAt(entity.getCompletedAt())
                .notes(entity.getNotes()).build();
    }
}
