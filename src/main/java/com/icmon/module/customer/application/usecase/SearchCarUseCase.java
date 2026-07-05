package com.icmon.module.customer.application.usecase;

import com.icmon.module.customer.application.interfaces.CarService;
import com.icmon.module.customer.presentation.dto.request.CarSearchRequestDTO;
import com.icmon.module.customer.presentation.dto.response.CarResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchCarUseCase {
    private final CarService carService;
    public Page<CarResponseDTO> execute(CarSearchRequestDTO request, Pageable pageable) throws SystemGlobalException {
        return carService.searchCars(request, pageable);
    }
}
