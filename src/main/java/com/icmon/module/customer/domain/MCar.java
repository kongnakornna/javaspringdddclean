package com.icmon.module.customer.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.customer.domain.enums.CarFuelType;
import com.icmon.module.customer.domain.enums.CarTransmissionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class MCar extends GenericBusinessClass {
    private UUID customerId;
    private String licensePlate;
    private String province;
    private String brand;
    private String model;
    private String subModel;
    private Integer year;
    private String color;
    private String engineNumber;
    private String chassisNumber;
    private CarFuelType fuelType;
    private CarTransmissionType transmissionType;
    private Integer engineCc;
    private Integer seatingCapacity;
    private Integer mileage;
    private LocalDateTime lastServiceDate;
    private Integer nextServiceMileage;
    private String notes;
    private Boolean isActive;

    public void updateMileage(Integer newMileage) {
        this.mileage = newMileage;
    }

    public boolean needsService() {
        if (nextServiceMileage == null || mileage == null) return false;
        return mileage >= nextServiceMileage;
    }
}
