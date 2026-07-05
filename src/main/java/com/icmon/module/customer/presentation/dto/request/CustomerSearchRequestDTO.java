package com.icmon.module.customer.presentation.dto.request;

import com.icmon.module.customer.domain.enums.CustomerStatus;
import com.icmon.module.customer.domain.enums.CustomerType;
import lombok.Data;

@Data
public class CustomerSearchRequestDTO {
    private String name;
    private String phone;
    private CustomerType customerType;
    private CustomerStatus status;
}
