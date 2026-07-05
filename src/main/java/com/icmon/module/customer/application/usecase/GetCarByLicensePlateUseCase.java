package com.icmon.module.customer.application.usecase;

import com.icmon.module.customer.application.interfaces.CarService;
import com.icmon.module.customer.presentation.dto.response.CarResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetCarByLicensePlateUseCase {
    private final CarService carService;
    public CarResponseDTO execute(String licensePlate) throws SystemGlobalException {
        return carService.getCarByPlate(licensePlate);
    }
}
