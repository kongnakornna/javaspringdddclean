package com.icmon.module.customer.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.customer.application.usecase.*;
import com.icmon.module.customer.presentation.dto.request.*;
import com.icmon.module.customer.presentation.dto.response.*;
import com.icmon.module.customer.presentation.validator.CarValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/*
    CarController - จัดการ API ยานพาหนะทั้งหมด
    CarController - Handles all vehicle-related APIs.
    Module: Customer Management (Module 3)
*/
@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
@Tag(name = "Vehicle Management", description = "APIs for managing vehicles")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CarController {

    private final CreateCarUseCase createCarUseCase;
    private final UpdateCarUseCase updateCarUseCase;
    private final GetCarUseCase getCarUseCase;
    private final DeleteCarUseCase deleteCarUseCase;
    private final GetCarByLicensePlateUseCase getCarByLicensePlateUseCase;
    private final GetCustomerCarsUseCase getCustomerCarsUseCase;
    private final SearchCarUseCase searchCarUseCase;
    private final CarValidator validator;

    /*
        สร้างรถใหม่ / Create new car
        Rate Limit: อนุญาต 30 ครั้งต่อ 1 นาที / Allows 30 attempts per 1 minute.
    */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "สร้างรถใหม่ / Create new car")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    public ResponseEntity<CarResponseDTO> createCar(@Valid @RequestBody CarCreateRequestDTO request) throws SystemGlobalException {
        validator.validateCreate(request);
        CarResponseDTO result = createCarUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /*
        ดึงข้อมูลรถ / Get car by ID
        Rate Limit: อนุญาต 100 ครั้งต่อ 1 นาที / Allows 100 attempts per 1 minute.
    */
    @GetMapping("/{id}")
    @Operation(summary = "ดึงข้อมูลรถ / Get car by ID")
    @RateLimit(limit = 100, duration = 60, keyType = "USER_ID")
    public ResponseEntity<CarResponseDTO> getCar(@PathVariable UUID id) throws SystemGlobalException {
        CarResponseDTO result = getCarUseCase.execute(id);
        return ResponseEntity.ok(result);
    }

    /*
        ดึงรถของลูกค้า / Get cars by customer
        Rate Limit: อนุญาต 60 ครั้งต่อ 1 นาที / Allows 60 attempts per 1 minute.
    */
    @GetMapping("/customer/{customerId}")
    @Operation(summary = "ดึงรถของลูกค้า / Get cars by customer")
    @RateLimit(limit = 60, duration = 60, keyType = "USER_ID")
    public ResponseEntity<List<CarResponseDTO>> getCarsByCustomer(@PathVariable UUID customerId) throws SystemGlobalException {
        List<CarResponseDTO> result = getCustomerCarsUseCase.execute(customerId);
        return ResponseEntity.ok(result);
    }

    /*
        ค้นหาจากทะเบียน / Get car by license plate
        Rate Limit: อนุญาต 60 ครั้งต่อ 1 นาที / Allows 60 attempts per 1 minute.
    */
    @GetMapping("/plate/{licensePlate}")
    @Operation(summary = "ค้นหาจากทะเบียน / Get car by license plate")
    @RateLimit(limit = 60, duration = 60, keyType = "USER_ID")
    public ResponseEntity<CarResponseDTO> getCarByPlate(@PathVariable String licensePlate) throws SystemGlobalException {
        CarResponseDTO result = getCarByLicensePlateUseCase.execute(licensePlate);
        return ResponseEntity.ok(result);
    }

    /*
        ค้นหา xe / Search cars (supports pagination)
        Rate Limit: อนุญาต 60 ครั้งต่อ 1 นาที / Allows 60 attempts per 1 minute.
    */
    @GetMapping("/search")
    @Operation(summary = "ค้นหา xe / Search cars")
    @RateLimit(limit = 60, duration = 60, keyType = "USER_ID")
    public ResponseEntity<Page<CarResponseDTO>> searchCars(
            CarSearchRequestDTO request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) throws SystemGlobalException {
        Page<CarResponseDTO> result = searchCarUseCase.execute(request, PageRequest.of(page, size));
        return ResponseEntity.ok(result);
    }

    /*
        อัพเดทข้อมูลรถ / Update car
        Rate Limit: อนุญาต 30 ครั้งต่อ 1 นาที / Allows 30 attempts per 1 minute.
    */
    @PutMapping("/{id}")
    @Operation(summary = "อัพเดทข้อมูลรถ / Update car")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    public ResponseEntity<CarResponseDTO> updateCar(
            @PathVariable UUID id,
            @Valid @RequestBody CarUpdateRequestDTO request) throws SystemGlobalException {
        CarResponseDTO result = updateCarUseCase.execute(id, request);
        return ResponseEntity.ok(result);
    }

    /*
        ลบรถ / Delete car (soft delete)
        Rate Limit: อนุญาต 10 ครั้งต่อ 1 นาที / Allows 10 attempts per 1 minute.
    */
    @DeleteMapping("/{id}")
    @Operation(summary = "ลบรถ / Delete car (soft delete)")
    @RateLimit(limit = 10, duration = 60, keyType = "USER_ID")
    public ResponseEntity<Void> deleteCar(@PathVariable UUID id) throws SystemGlobalException {
        deleteCarUseCase.execute(id);
        return ResponseEntity.ok().build();
    }
}
