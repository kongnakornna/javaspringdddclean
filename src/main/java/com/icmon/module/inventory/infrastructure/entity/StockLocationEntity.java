package com.icmon.module.inventory.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "m_stock_location")
@Getter
@Setter
public class StockLocationEntity extends GenericBusinessEntity {

    @Column(name = "location_code", unique = true, nullable = false, length = 20)
    private String locationCode;

    @Column(name = "location_name", nullable = false, length = 100)
    private String locationName;

    @Column(name = "location_type", length = 20)
    private String locationType;

    @Column(length = 50)
    private String zone;

    private Integer capacity;

    @Column(name = "current_usage")
    private Integer currentUsage;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
