package com.icmon.module.inventory.presentation.dto.response;

import com.icmon.module.inventory.infrastructure.entity.StockAdjustmentHeaderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdjustmentResponseDTO {
    private UUID id;
    private String adjustmentNo;
    private String adjustmentType;
    private String reason;
    private String status;
    private String description;
    private BigDecimal totalAdjustmentValue;
    private LocalDateTime adjustmentDate;
    private UUID approvedBy;
    private LocalDateTime approvedAt;

    public static AdjustmentResponseDTO fromEntity(StockAdjustmentHeaderEntity entity) {
        return AdjustmentResponseDTO.builder()
                .id(entity.getId()).adjustmentNo(entity.getAdjustmentNo())
                .adjustmentType(entity.getAdjustmentType()).reason(entity.getReason())
                .status(entity.getStatus()).description(entity.getDescription())
                .totalAdjustmentValue(entity.getTotalAdjustmentValue())
                .adjustmentDate(entity.getAdjustmentDate())
                .approvedBy(entity.getApprovedBy()).approvedAt(entity.getApprovedAt())
                .build();
    }
}
