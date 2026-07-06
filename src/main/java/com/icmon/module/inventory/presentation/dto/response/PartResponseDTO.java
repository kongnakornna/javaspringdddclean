package com.icmon.module.inventory.presentation.dto.response;

import com.icmon.module.inventory.infrastructure.entity.PartMasterEntity;
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
public class PartResponseDTO {
    private UUID id;
    private String partCode;
    private String partName;
    private String partNameEn;
    private UUID categoryId;
    private String brand;
    private String model;
    private String oemNumber;
    private String description;
    private String unit;
    private Integer reorderLevel;
    private Integer reorderQuantity;
    private Integer stockQuantity;
    private Integer minStock;
    private Integer maxStock;
    private BigDecimal unitCost;
    private BigDecimal sellingPrice;
    private UUID locationId;
    private String status;
    private LocalDateTime lastUpdatedStock;

    public static PartResponseDTO fromEntity(PartMasterEntity entity) {
        return PartResponseDTO.builder()
                .id(entity.getId()).partCode(entity.getPartCode())
                .partName(entity.getPartName()).partNameEn(entity.getPartNameEn())
                .categoryId(entity.getCategoryId()).brand(entity.getBrand())
                .model(entity.getModel()).oemNumber(entity.getOemNumber())
                .description(entity.getDescription()).unit(entity.getUnit())
                .reorderLevel(entity.getReorderLevel()).reorderQuantity(entity.getReorderQuantity())
                .stockQuantity(entity.getStockQuantity()).minStock(entity.getMinStock())
                .maxStock(entity.getMaxStock()).unitCost(entity.getUnitCost())
                .sellingPrice(entity.getSellingPrice()).locationId(entity.getLocationId())
                .status(entity.getStatus()).lastUpdatedStock(entity.getLastUpdatedStock())
                .build();
    }
}
