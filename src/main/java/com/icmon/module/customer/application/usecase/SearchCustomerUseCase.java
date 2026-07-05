package com.icmon.module.customer.application.usecase;

import com.icmon.module.customer.application.interfaces.CustomerService;
import com.icmon.module.customer.presentation.dto.request.CustomerSearchRequestDTO;
import com.icmon.module.customer.presentation.dto.response.CustomerResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchCustomerUseCase {
    private final CustomerService customerService;
    public Page<CustomerResponseDTO> execute(CustomerSearchRequestDTO request, Pageable pageable) throws SystemGlobalException {
        return customerService.searchCustomers(request, pageable);
    }
}
