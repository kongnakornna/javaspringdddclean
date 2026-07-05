package com.icmon.module.customer.application.usecase;

import com.icmon.module.customer.application.interfaces.CarService;
import com.icmon.module.customer.presentation.dto.response.CarResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetCustomerCarsUseCase {
    private final CarService carService;
    public List<CarResponseDTO> execute(UUID customerId) throws SystemGlobalException {
        return carService.getCarsByCustomer(customerId);
    }
}
