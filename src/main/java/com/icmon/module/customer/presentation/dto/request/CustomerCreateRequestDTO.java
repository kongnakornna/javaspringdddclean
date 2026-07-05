package com.icmon.module.customer.presentation.dto.request;

import com.icmon.module.customer.domain.enums.CustomerType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CustomerCreateRequestDTO {
    @NotBlank(message = "Full name is required")
    private String fullName;
    private String displayName;
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    private String secondaryPhone;
    @Email(message = "Invalid email format")
    private String email;
    @NotNull(message = "Customer type is required")
    private CustomerType customerType;
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
