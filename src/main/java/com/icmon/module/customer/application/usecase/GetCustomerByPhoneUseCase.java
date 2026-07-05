package com.icmon.module.customer.application.usecase;

import com.icmon.module.customer.application.interfaces.CustomerService;
import com.icmon.module.customer.presentation.dto.response.CustomerResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetCustomerByPhoneUseCase {
    private final CustomerService customerService;
    public CustomerResponseDTO execute(String phone) throws SystemGlobalException {
        return customerService.getCustomerByPhone(phone);
    }
}
