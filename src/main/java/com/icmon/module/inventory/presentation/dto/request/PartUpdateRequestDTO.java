package com.icmon.module.inventory.presentation.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PartUpdateRequestDTO {
    private String partName;
    private String partNameEn;
    private String brand;
    private String model;
    private String description;
    private BigDecimal unitCost;
    private BigDecimal sellingPrice;
    private Integer reorderLevel;
    private Integer reorderQuantity;
    private Integer minStock;
    private Integer maxStock;
    private String status;
    private UUID locationId;
}
