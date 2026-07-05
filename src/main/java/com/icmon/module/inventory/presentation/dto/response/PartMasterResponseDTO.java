package com.icmon.module.inventory.presentation.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PartMasterResponseDTO {
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
    private int reorderLevel;
    private int reorderQuantity;
    private int stockQuantity;
    private int minStock;
    private int maxStock;
    private BigDecimal unitCost;
    private BigDecimal sellingPrice;
    private UUID locationId;
    private String status;
    private String imageUrl;
    private String notes;
    private LocalDateTime lastUpdatedStock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
