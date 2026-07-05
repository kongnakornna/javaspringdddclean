package com.icmon.module.customer.application.usecase;

import com.icmon.module.customer.application.interfaces.CarService;
import com.icmon.module.customer.presentation.dto.request.CarUpdateRequestDTO;
import com.icmon.module.customer.presentation.dto.response.CarResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateCarUseCase {
    private final CarService carService;
    public CarResponseDTO execute(UUID id, CarUpdateRequestDTO request) throws SystemGlobalException {
        return carService.updateCar(id, request);
    }
}
