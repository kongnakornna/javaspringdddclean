package com.icmon.module.customer.application.usecase;

import com.icmon.module.customer.application.interfaces.CustomerService;
import com.icmon.module.customer.presentation.dto.response.CustomerDetailResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetCustomerUseCase {
    private final CustomerService customerService;
    public CustomerDetailResponseDTO execute(UUID id) throws SystemGlobalException {
        return customerService.getCustomer(id);
    }
}
