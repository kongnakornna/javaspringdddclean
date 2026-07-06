package com.icmon.module.inventory.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class MPartMaster extends GenericBusinessClass {
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
    private String imageUrl;
    private String notes;
    private LocalDateTime lastUpdatedStock;

    public boolean isLowStock() {
        return stockQuantity != null && reorderLevel != null
                && stockQuantity <= reorderLevel;
    }

    public void increaseStock(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        this.stockQuantity = (this.stockQuantity != null ? this.stockQuantity : 0) + quantity;
        this.lastUpdatedStock = LocalDateTime.now();
    }

    public void decreaseStock(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        if (this.stockQuantity < quantity) {
            throw new IllegalArgumentException("Insufficient stock. Available: " + this.stockQuantity);
        }
        this.stockQuantity -= quantity;
        this.lastUpdatedStock = LocalDateTime.now();
    }
}
