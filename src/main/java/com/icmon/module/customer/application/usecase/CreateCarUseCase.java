package com.icmon.module.customer.application.usecase;

import com.icmon.module.customer.application.interfaces.CarService;
import com.icmon.module.customer.presentation.dto.request.CarCreateRequestDTO;
import com.icmon.module.customer.presentation.dto.response.CarResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCarUseCase {
    private final CarService carService;
    public CarResponseDTO execute(CarCreateRequestDTO request) throws SystemGlobalException {
        return carService.createCar(request);
    }
}
