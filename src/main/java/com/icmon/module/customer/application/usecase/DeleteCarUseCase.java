package com.icmon.module.customer.application.usecase;

import com.icmon.module.customer.application.interfaces.CarService;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteCarUseCase {
    private final CarService carService;
    public void execute(UUID id) throws SystemGlobalException {
        carService.deleteCar(id);
    }
}
