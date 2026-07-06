package com.icmon.module.weborder.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@Entity
@Table(name = "m_catalogue_category")
@EqualsAndHashCode(callSuper = true)
public class CatalogueCategoryEntity extends GenericBusinessEntity {

    @Column(name = "category_code", unique = true, nullable = false, length = 50)
    private String categoryCode;

    @Column(name = "category_name", nullable = false, length = 100)
    private String categoryName;

    @Column(name = "category_name_en", length = 100)
    private String categoryNameEn;

    @Column(name = "parent_id")
    private UUID parentId;

    @Column
    private Integer level;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "icon_url", columnDefinition = "TEXT")
    private String iconUrl;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(columnDefinition = "TEXT")
    private String description;
}
