package com.icmon.module.weborder.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class MCatalogueItem extends GenericBusinessClass {

    private String itemCode;
    private String itemName;
    private String itemNameEn;
    private UUID categoryId;
    private UUID partId;
    private String description;
    private String shortDescription;
    private String brand;
    private String modelCompatibility;
    private String imageUrl;
    private String galleryImages;
    private Boolean isActive;
    private Boolean isFeatured;
    private Boolean isNew;
    private Integer sortOrder;
    private String[] tags;
    private String metadata;

    public boolean isAvailable() {
        return this.isActive != null && this.isActive;
    }

    public boolean shouldFeature() {
        return this.isFeatured != null && this.isFeatured && this.isAvailable();
    }
}
