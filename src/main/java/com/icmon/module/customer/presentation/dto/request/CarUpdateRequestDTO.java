package com.icmon.module.customer.presentation.dto.request;

import com.icmon.module.customer.domain.enums.CarFuelType;
import com.icmon.module.customer.domain.enums.CarTransmissionType;
import lombok.Data;

@Data
public class CarUpdateRequestDTO {
    private String licensePlate;
    private String province;
    private String brand;
    private String model;
    private String subModel;
    private Integer year;
    private String color;
    private CarFuelType fuelType;
    private CarTransmissionType transmissionType;
    private Integer engineCc;
    private Integer mileage;
    private String notes;
}
