package com.icmon.module.inventory.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PartCreateRequestDTO {
    @NotBlank @Size(max = 50)
    private String partCode;
    @NotBlank @Size(max = 200)
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
    private Integer minStock;
    private Integer maxStock;
    private BigDecimal unitCost;
    private BigDecimal sellingPrice;
    private UUID locationId;
}
