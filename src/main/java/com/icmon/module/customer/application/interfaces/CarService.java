package com.icmon.module.customer.application.interfaces;

import com.icmon.module.customer.presentation.dto.request.*;
import com.icmon.module.customer.presentation.dto.response.*;
import com.icmon.exception.SystemGlobalException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CarService {
    CarResponseDTO createCar(CarCreateRequestDTO request) throws SystemGlobalException;
    CarResponseDTO getCar(UUID id) throws SystemGlobalException;
    List<CarResponseDTO> getCarsByCustomer(UUID customerId) throws SystemGlobalException;
    CarResponseDTO getCarByPlate(String licensePlate) throws SystemGlobalException;
    Page<CarResponseDTO> searchCars(CarSearchRequestDTO request, Pageable pageable) throws SystemGlobalException;
    CarResponseDTO updateCar(UUID id, CarUpdateRequestDTO request) throws SystemGlobalException;
    void deleteCar(UUID id) throws SystemGlobalException;
}
