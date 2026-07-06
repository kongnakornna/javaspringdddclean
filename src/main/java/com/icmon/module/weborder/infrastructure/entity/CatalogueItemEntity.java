package com.icmon.module.weborder.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@Entity
@Table(name = "m_catalogue_item")
@EqualsAndHashCode(callSuper = true)
public class CatalogueItemEntity extends GenericBusinessEntity {

    @Column(name = "item_code", unique = true, nullable = false, length = 50)
    private String itemCode;

    @Column(name = "item_name", nullable = false, length = 200)
    private String itemName;

    @Column(name = "item_name_en", length = 200)
    private String itemNameEn;

    @Column(name = "category_id")
    private UUID categoryId;

    @Column(name = "part_id")
    private UUID partId;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "short_description", length = 500)
    private String shortDescription;

    @Column(length = 100)
    private String brand;

    @Column(name = "model_compatibility", columnDefinition = "TEXT")
    private String modelCompatibility;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "gallery_images", columnDefinition = "JSONB")
    private String galleryImages;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_featured")
    private Boolean isFeatured;

    @Column(name = "is_new")
    private Boolean isNew;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(columnDefinition = "TEXT[]")
    private String[] tags;

    @Column(columnDefinition = "JSONB")
    private String metadata;
}
