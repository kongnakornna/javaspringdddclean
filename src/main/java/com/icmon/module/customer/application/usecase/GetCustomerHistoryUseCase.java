package com.icmon.module.customer.application.usecase;

import com.icmon.module.customer.application.interfaces.CustomerService;
import com.icmon.module.customer.presentation.dto.response.CustomerHistoryResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetCustomerHistoryUseCase {
    private final CustomerService customerService;
    public List<CustomerHistoryResponseDTO> execute(UUID id) throws SystemGlobalException {
        return customerService.getCustomerHistory(id);
    }
}
