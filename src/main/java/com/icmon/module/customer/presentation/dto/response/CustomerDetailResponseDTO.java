package com.icmon.module.customer.presentation.dto.response;

import com.icmon.module.customer.domain.enums.CustomerStatus;
import com.icmon.module.customer.domain.enums.CustomerType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CustomerDetailResponseDTO {
    private UUID id;
    private String customerCode;
    private String fullName;
    private String displayName;
    private CustomerType customerType;
    private CustomerStatus status;
    private String taxId;
    private String email;
    private String phoneNumber;
    private String secondaryPhone;
    private String address;
    private String province;
    private String city;
    private String district;
    private String postalCode;
    private String country;
    private String contactPerson;
    private String contactPhone;
    private String notes;
    private LocalDateTime lastVisitDate;
    private Integer totalVisitCount;
    private BigDecimal totalSpent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
