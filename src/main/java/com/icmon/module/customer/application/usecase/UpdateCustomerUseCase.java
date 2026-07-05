package com.icmon.module.customer.application.usecase;

import com.icmon.module.customer.application.interfaces.CustomerService;
import com.icmon.module.customer.presentation.dto.request.CustomerUpdateRequestDTO;
import com.icmon.module.customer.presentation.dto.response.CustomerResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateCustomerUseCase {
    private final CustomerService customerService;
    public CustomerResponseDTO execute(UUID id, CustomerUpdateRequestDTO request) throws SystemGlobalException {
        return customerService.updateCustomer(id, request);
    }
}
