package com.icmon.module.inventory.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "m_part_master")
@EqualsAndHashCode(callSuper = true)
public class PartMasterEntity extends GenericBusinessEntity {
    @Column(name = "part_code", unique = true, nullable = false, length = 50)
    private String partCode;
    @Column(name = "part_name", nullable = false, length = 200)
    private String partName;
    @Column(name = "part_name_en", length = 200)
    private String partNameEn;
    @Column(name = "category_id")
    private UUID categoryId;
    @Column(length = 50)
    private String brand;
    @Column(length = 100)
    private String model;
    @Column(name = "oem_number", length = 50)
    private String oemNumber;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(length = 20)
    private String unit;
    @Column(name = "reorder_level")
    private Integer reorderLevel;
    @Column(name = "reorder_quantity")
    private Integer reorderQuantity;
    @Column(name = "stock_quantity")
    private Integer stockQuantity;
    @Column(name = "min_stock")
    private Integer minStock;
    @Column(name = "max_stock")
    private Integer maxStock;
    @Column(name = "unit_cost", precision = 15, scale = 2)
    private BigDecimal unitCost;
    @Column(name = "selling_price", precision = 15, scale = 2)
    private BigDecimal sellingPrice;
    @Column(name = "location_id")
    private UUID locationId;
    @Column(length = 20)
    private String status;
    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;
    @Column(columnDefinition = "TEXT")
    private String notes;
    @Column(name = "last_updated_stock")
    private LocalDateTime lastUpdatedStock;
}
