package com.icmon.module.customer.application.usecase;

import com.icmon.module.customer.application.interfaces.CarService;
import com.icmon.module.customer.presentation.dto.response.CarResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetCarUseCase {
    private final CarService carService;
    public CarResponseDTO execute(UUID id) throws SystemGlobalException {
        return carService.getCar(id);
    }
}
