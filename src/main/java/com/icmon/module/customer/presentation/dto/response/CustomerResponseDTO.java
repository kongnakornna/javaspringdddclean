package com.icmon.module.customer.presentation.dto.response;

import com.icmon.module.customer.domain.enums.CustomerStatus;
import com.icmon.module.customer.domain.enums.CustomerType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CustomerResponseDTO {
    private UUID id;
    private String customerCode;
    private String fullName;
    private String displayName;
    private CustomerType customerType;
    private CustomerStatus status;
    private String email;
    private String phoneNumber;
    private String address;
    private String province;
    private String city;
    private LocalDateTime createdAt;
}
