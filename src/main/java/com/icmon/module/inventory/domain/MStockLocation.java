package com.icmon.module.inventory.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MStockLocation extends GenericBusinessClass {
    private String locationCode;
    private String locationName;
    private String locationType;
    private String zone;
    private Integer capacity;
    private Integer currentUsage;
    private Boolean isActive;
    private String notes;
}
