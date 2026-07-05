package com.icmon.module.customer.presentation.dto.request;

import com.icmon.module.customer.domain.enums.CarFuelType;
import com.icmon.module.customer.domain.enums.CarTransmissionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CarCreateRequestDTO {
    @NotNull(message = "Customer ID is required")
    private UUID customerId;
    @NotBlank(message = "License plate is required")
    private String licensePlate;
    private String province;
    @NotBlank(message = "Brand is required")
    private String brand;
    @NotBlank(message = "Model is required")
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
    private String notes;
}
