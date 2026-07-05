package com.icmon.module.customer.application.usecase;

import com.icmon.module.customer.application.interfaces.CustomerService;
import com.icmon.module.customer.presentation.dto.request.CustomerCreateRequestDTO;
import com.icmon.module.customer.presentation.dto.response.CustomerResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCustomerUseCase {
    private final CustomerService customerService;
    public CustomerResponseDTO execute(CustomerCreateRequestDTO request) throws SystemGlobalException {
        return customerService.createCustomer(request);
    }
}
