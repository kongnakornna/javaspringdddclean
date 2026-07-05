package com.icmon.module.inventory.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.inventory.domain.enums.PartStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PartMaster extends GenericBusinessClass {
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
    private PartStatus status;
    private String imageUrl;
    private String notes;
    private LocalDateTime lastUpdatedStock;

    public PartMaster() {}

    public PartMaster(UUID id, String partCode, String partName) {
        setId(id);
        this.partCode = partCode;
        this.partName = partName;
        this.status = PartStatus.ACTIVE;
        this.unit = "PIECE";
    }

    public String getPartCode() { return partCode; }
    public void setPartCode(String partCode) { this.partCode = partCode; }
    public String getPartName() { return partName; }
    public void setPartName(String partName) { this.partName = partName; }
    public String getPartNameEn() { return partNameEn; }
    public void setPartNameEn(String partNameEn) { this.partNameEn = partNameEn; }
    public UUID getCategoryId() { return categoryId; }
    public void setCategoryId(UUID categoryId) { this.categoryId = categoryId; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getOemNumber() { return oemNumber; }
    public void setOemNumber(String oemNumber) { this.oemNumber = oemNumber; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public int getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(int reorderLevel) { this.reorderLevel = reorderLevel; }
    public int getReorderQuantity() { return reorderQuantity; }
    public void setReorderQuantity(int reorderQuantity) { this.reorderQuantity = reorderQuantity; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    public int getMinStock() { return minStock; }
    public void setMinStock(int minStock) { this.minStock = minStock; }
    public int getMaxStock() { return maxStock; }
    public void setMaxStock(int maxStock) { this.maxStock = maxStock; }
    public BigDecimal getUnitCost() { return unitCost; }
    public void setUnitCost(BigDecimal unitCost) { this.unitCost = unitCost; }
    public BigDecimal getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(BigDecimal sellingPrice) { this.sellingPrice = sellingPrice; }
    public UUID getLocationId() { return locationId; }
    public void setLocationId(UUID locationId) { this.locationId = locationId; }
    public PartStatus getStatus() { return status; }
    public void setStatus(PartStatus status) { this.status = status; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDateTime getLastUpdatedStock() { return lastUpdatedStock; }
    public void setLastUpdatedStock(LocalDateTime lastUpdatedStock) { this.lastUpdatedStock = lastUpdatedStock; }
}
