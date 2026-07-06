package com.icmon.module.weborder.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class MCatalogueCategory extends GenericBusinessClass {

    private String categoryCode;
    private String categoryName;
    private String categoryNameEn;
    private UUID parentId;
    private Integer level;
    private Integer sortOrder;
    private String iconUrl;
    private Boolean isActive;
    private String description;
}
