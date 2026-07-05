package com.icmon.module.customer.application.usecase;

import com.icmon.module.customer.application.interfaces.CustomerService;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteCustomerUseCase {
    private final CustomerService customerService;
    public void execute(UUID id) throws SystemGlobalException {
        customerService.deleteCustomer(id);
    }
}
