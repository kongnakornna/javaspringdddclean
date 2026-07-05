package com.icmon.module.customer.presentation.dto.response;

import com.icmon.module.customer.domain.enums.CarFuelType;
import com.icmon.module.customer.domain.enums.CarTransmissionType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CarResponseDTO {
    private UUID id;
    private UUID customerId;
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
    private Boolean isActive;
    private LocalDateTime createdAt;
}
