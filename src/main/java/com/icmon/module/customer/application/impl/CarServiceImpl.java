package com.icmon.module.customer.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.module.customer.application.interfaces.CarService;
import com.icmon.module.customer.domain.MCar;
import com.icmon.module.customer.infrastructure.cache.CarCacheService;
import com.icmon.module.customer.infrastructure.entity.CarEntity;
import com.icmon.module.customer.infrastructure.mapper.CarMapper;
import com.icmon.module.customer.infrastructure.repository.CarRepository;
import com.icmon.module.customer.presentation.dto.request.*;
import com.icmon.module.customer.presentation.dto.response.*;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarServiceImpl extends GenericAuthDomainServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final CarCacheService cacheService;

    @Override
    public CarResponseDTO createCar(CarCreateRequestDTO request) throws SystemGlobalException {
        if (carRepository.existsByLicensePlate(request.getLicensePlate())) {
            throw new FailedRequestException("License plate already registered.", null);
        }

        CarEntity entity = new CarEntity();
        entity.setCustomerId(request.getCustomerId());
        entity.setLicensePlate(request.getLicensePlate());
        entity.setProvince(request.getProvince());
        entity.setBrand(request.getBrand());
        entity.setModel(request.getModel());
        entity.setSubModel(request.getSubModel());
        entity.setYear(request.getYear());
        entity.setColor(request.getColor());
        entity.setEngineNumber(request.getEngineNumber());
        entity.setChassisNumber(request.getChassisNumber());
        entity.setFuelType(request.getFuelType());
        entity.setTransmissionType(request.getTransmissionType());
        entity.setEngineCc(request.getEngineCc());
        entity.setSeatingCapacity(request.getSeatingCapacity());
        entity.setMileage(0);
        entity.setIsActive(true);
        entity.setUserId(getUserId());
        entity.setWhitelabelId(getWhitelabelId());

        CarEntity saved = carRepository.save(entity);
        MCar domain = carMapper.toDomain(saved);
        cacheService.saveCar(domain);
        return toResponseDTO(domain);
    }

    @Override
    public CarResponseDTO getCar(UUID id) throws SystemGlobalException {
        CarEntity entity = carRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Car not found with id: " + id, null));
        return toResponseDTO(carMapper.toDomain(entity));
    }

    @Override
    public List<CarResponseDTO> getCarsByCustomer(UUID customerId) throws SystemGlobalException {
        List<CarEntity> cars = carRepository.findByCustomerIdAndDeletedFalse(customerId);
        return cars.stream()
                .map(e -> toResponseDTO(carMapper.toDomain(e)))
                .collect(Collectors.toList());
    }

    @Override
    public CarResponseDTO getCarByPlate(String licensePlate) throws SystemGlobalException {
        MCar cached = cacheService.getCarByPlate(licensePlate);
        if (cached != null) return toResponseDTO(cached);

        CarEntity entity = carRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new FailedRequestException("Car not found with plate: " + licensePlate, null));
        MCar domain = carMapper.toDomain(entity);
        cacheService.saveCar(domain);
        return toResponseDTO(domain);
    }

    @Override
    public Page<CarResponseDTO> searchCars(CarSearchRequestDTO request, Pageable pageable) throws SystemGlobalException {
        Page<CarEntity> page = carRepository.searchCars(
                request.getBrand(), request.getModel(), request.getYear(), pageable);
        return page.map(e -> toResponseDTO(carMapper.toDomain(e)));
    }

    @Override
    public CarResponseDTO updateCar(UUID id, CarUpdateRequestDTO request) throws SystemGlobalException {
        CarEntity entity = carRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Car not found with id: " + id, null));

        if (request.getLicensePlate() != null) entity.setLicensePlate(request.getLicensePlate());
        if (request.getProvince() != null) entity.setProvince(request.getProvince());
        if (request.getBrand() != null) entity.setBrand(request.getBrand());
        if (request.getModel() != null) entity.setModel(request.getModel());
        if (request.getSubModel() != null) entity.setSubModel(request.getSubModel());
        if (request.getYear() != null) entity.setYear(request.getYear());
        if (request.getColor() != null) entity.setColor(request.getColor());
        if (request.getFuelType() != null) entity.setFuelType(request.getFuelType());
        if (request.getTransmissionType() != null) entity.setTransmissionType(request.getTransmissionType());
        if (request.getEngineCc() != null) entity.setEngineCc(request.getEngineCc());
        if (request.getMileage() != null) entity.setMileage(request.getMileage());
        if (request.getNotes() != null) entity.setNotes(request.getNotes());

        CarEntity saved = carRepository.save(entity);
        MCar domain = carMapper.toDomain(saved);
        cacheService.saveCar(domain);
        return toResponseDTO(domain);
    }

    @Override
    public void deleteCar(UUID id) throws SystemGlobalException {
        CarEntity entity = carRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Car not found with id: " + id, null));
        entity.setDeleted(true);
        entity.setDeletedAt(java.time.LocalDateTime.now());
        carRepository.save(entity);
        cacheService.evictCar(id);
    }

    private CarResponseDTO toResponseDTO(MCar domain) {
        return CarResponseDTO.builder()
                .id(domain.getId())
                .customerId(domain.getCustomerId())
                .licensePlate(domain.getLicensePlate())
                .province(domain.getProvince())
                .brand(domain.getBrand())
                .model(domain.getModel())
                .subModel(domain.getSubModel())
                .year(domain.getYear())
                .color(domain.getColor())
                .fuelType(domain.getFuelType())
                .transmissionType(domain.getTransmissionType())
                .engineCc(domain.getEngineCc())
                .mileage(domain.getMileage())
                .isActive(domain.getIsActive())
                .createdAt(domain.getCreatedAt())
                .build();
    }
}
