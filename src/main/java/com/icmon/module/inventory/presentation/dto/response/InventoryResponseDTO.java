package com.icmon.module.inventory.presentation.dto.response;

import com.icmon.module.inventory.domain.MPartMaster;
import com.icmon.module.inventory.infrastructure.entity.InventoryEntity;
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
public class InventoryResponseDTO {
    private UUID id;
    private UUID partId;
    private String partCode;
    private String partName;
    private String transactionType;
    private String referenceType;
    private UUID referenceId;
    private Integer quantity;
    private Integer previousQuantity;
    private Integer newQuantity;
    private BigDecimal unitCost;
    private BigDecimal totalCost;
    private LocalDateTime transactionDate;
    private String note;

    public static InventoryResponseDTO fromEntity(InventoryEntity entity, MPartMaster part) {
        return InventoryResponseDTO.builder()
                .id(entity.getId()).partId(entity.getPartId())
                .partCode(part != null ? part.getPartCode() : null)
                .partName(part != null ? part.getPartName() : null)
                .transactionType(entity.getTransactionType())
                .referenceType(entity.getReferenceType()).referenceId(entity.getReferenceId())
                .quantity(entity.getQuantity()).previousQuantity(entity.getPreviousQuantity())
                .newQuantity(entity.getNewQuantity()).unitCost(entity.getUnitCost())
                .totalCost(entity.getTotalCost()).transactionDate(entity.getTransactionDate())
                .note(entity.getNote()).build();
    }
}
