package com.icmon.module.customer.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "m_car_service_history")
public class CarServiceHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "car_id", nullable = false)
    private UUID carId;

    @Column(name = "job_id", nullable = false)
    private UUID jobId;

    @Column(name = "service_date", nullable = false)
    private LocalDateTime serviceDate;

    @Column(name = "service_type")
    private String serviceType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "total_cost", precision = 15, scale = 2)
    private BigDecimal totalCost;

    @Column(name = "mileage_at_service")
    private Integer mileageAtService;

    @Column(name = "mechanic_name")
    private String mechanicName;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "whitelabel_id", nullable = false)
    private UUID whitelabelId;
}
