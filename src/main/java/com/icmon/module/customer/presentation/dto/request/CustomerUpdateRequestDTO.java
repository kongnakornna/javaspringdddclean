package com.icmon.module.customer.presentation.dto.request;

import com.icmon.module.customer.domain.enums.CustomerStatus;
import com.icmon.module.customer.domain.enums.CustomerType;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class CustomerUpdateRequestDTO {
    private String fullName;
    private String displayName;
    private String phoneNumber;
    private String secondaryPhone;
    @Email(message = "Invalid email format")
    private String email;
    private CustomerType customerType;
    private CustomerStatus status;
    private String taxId;
    private String address;
    private String province;
    private String city;
    private String district;
    private String postalCode;
    private String country;
    private String contactPerson;
    private String contactPhone;
    private String notes;
}
