package com.icmon.module.customer.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import com.icmon.module.customer.domain.enums.CarFuelType;
import com.icmon.module.customer.domain.enums.CarTransmissionType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "m_car")
@EqualsAndHashCode(callSuper = true)
public class CarEntity extends GenericBusinessEntity {
    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "license_plate", unique = true, nullable = false)
    private String licensePlate;

    @Column(length = 50)
    private String province;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(name = "sub_model")
    private String subModel;

    private Integer year;

    @Column(length = 30)
    private String color;

    @Column(name = "engine_number")
    private String engineNumber;

    @Column(name = "chassis_number")
    private String chassisNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type")
    private CarFuelType fuelType;

    @Enumerated(EnumType.STRING)
    @Column(name = "transmission_type")
    private CarTransmissionType transmissionType;

    @Column(name = "engine_cc")
    private Integer engineCc;

    @Column(name = "seating_capacity")
    private Integer seatingCapacity;

    @Column(columnDefinition = "INTEGER DEFAULT 0")
    private Integer mileage;

    @Column(name = "last_service_date")
    private LocalDateTime lastServiceDate;

    @Column(name = "next_service_mileage")
    private Integer nextServiceMileage;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "is_active")
    private Boolean isActive;
}
