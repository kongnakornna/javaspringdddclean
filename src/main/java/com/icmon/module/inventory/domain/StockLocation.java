package com.icmon.module.inventory.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.inventory.domain.enums.LocationType;
import java.util.UUID;

public class StockLocation extends GenericBusinessClass {
    private String locationCode;
    private String locationName;
    private LocationType locationType;
    private String zone;
    private Integer capacity;
    private Integer currentUsage;
    private boolean isActive;
    private String notes;

    public StockLocation() {}

    public StockLocation(UUID id, String locationCode, String locationName) {
        setId(id);
        this.locationCode = locationCode;
        this.locationName = locationName;
        this.locationType = LocationType.SHELF;
        this.isActive = true;
        this.currentUsage = 0;
    }

    public String getLocationCode() { return locationCode; }
    public void setLocationCode(String locationCode) { this.locationCode = locationCode; }
    public String getLocationName() { return locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }
    public LocationType getLocationType() { return locationType; }
    public void setLocationType(LocationType locationType) { this.locationType = locationType; }
    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public Integer getCurrentUsage() { return currentUsage; }
    public void setCurrentUsage(Integer currentUsage) { this.currentUsage = currentUsage; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
