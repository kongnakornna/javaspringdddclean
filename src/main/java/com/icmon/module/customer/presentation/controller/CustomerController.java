package com.icmon.module.customer.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.customer.application.usecase.*;
import com.icmon.module.customer.presentation.dto.request.*;
import com.icmon.module.customer.presentation.dto.response.*;
import com.icmon.module.customer.presentation.validator.CustomerValidator;
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
    CustomerController - จัดการ API ลูกค้าทั้งหมด
    CustomerController - Handles all customer-related APIs.
    Module: Customer Management (Module 3)
*/
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Customer Management", description = "APIs for managing customers")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final GetCustomerUseCase getCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;
    private final SearchCustomerUseCase searchCustomerUseCase;
    private final GetCustomerHistoryUseCase getCustomerHistoryUseCase;
    private final GetCustomerByPhoneUseCase getCustomerByPhoneUseCase;
    private final CustomerValidator validator;

    /*
        สร้างลูกค้าใหม่ / Create new customer
        Rate Limit: อนุญาต 30 ครั้งต่อ 1 นาที / Allows 30 attempts per 1 minute.
    */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "สร้างลูกค้าใหม่ / Create new customer")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    public ResponseEntity<CustomerResponseDTO> createCustomer(@Valid @RequestBody CustomerCreateRequestDTO request) throws SystemGlobalException {
        validator.validateCreate(request);
        CustomerResponseDTO result = createCustomerUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /*
        ดึงข้อมูลลูกค้า / Get customer by ID
        Rate Limit: อนุญาต 100 ครั้งต่อ 1 นาที / Allows 100 attempts per 1 minute.
    */
    @GetMapping("/{id}")
    @Operation(summary = "ดึงข้อมูลลูกค้า / Get customer by ID")
    @RateLimit(limit = 100, duration = 60, keyType = "USER_ID")
    public ResponseEntity<CustomerDetailResponseDTO> getCustomer(@PathVariable UUID id) throws SystemGlobalException {
        CustomerDetailResponseDTO result = getCustomerUseCase.execute(id);
        return ResponseEntity.ok(result);
    }

    /*
        ค้นหาลูกค้า / Search customers (supports pagination)
        Rate Limit: อนุญาต 60 ครั้งต่อ 1 นาที / Allows 60 attempts per 1 minute.
    */
    @GetMapping
    @Operation(summary = "ค้นหาลูกค้า / Search customers")
    @RateLimit(limit = 60, duration = 60, keyType = "USER_ID")
    public ResponseEntity<Page<CustomerResponseDTO>> searchCustomers(
            CustomerSearchRequestDTO request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) throws SystemGlobalException {
        Page<CustomerResponseDTO> result = searchCustomerUseCase.execute(request, PageRequest.of(page, size));
        return ResponseEntity.ok(result);
    }

    /*
        อัพเดทข้อมูลลูกค้า / Update customer
        Rate Limit: อนุญาต 30 ครั้งต่อ 1 นาที / Allows 30 attempts per 1 minute.
    */
    @PutMapping("/{id}")
    @Operation(summary = "อัพเดทข้อมูลลูกค้า / Update customer")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(
            @PathVariable UUID id,
            @Valid @RequestBody CustomerUpdateRequestDTO request) throws SystemGlobalException {
        validator.validateUpdate(id, request);
        CustomerResponseDTO result = updateCustomerUseCase.execute(id, request);
        return ResponseEntity.ok(result);
    }

    /*
        ลบลูกค้า / Delete customer (soft delete)
        Rate Limit: อนุญาต 10 ครั้งต่อ 1 นาที / Allows 10 attempts per 1 minute.
    */
    @DeleteMapping("/{id}")
    @Operation(summary = "ลบลูกค้า / Delete customer (soft delete)")
    @RateLimit(limit = 10, duration = 60, keyType = "USER_ID")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) throws SystemGlobalException {
        deleteCustomerUseCase.execute(id);
        return ResponseEntity.ok().build();
    }

    /*
        ดึงประวัติการบริการ / Get customer service history
        Rate Limit: อนุญาต 60 ครั้งต่อ 1 นาที / Allows 60 attempts per 1 minute.
    */
    @GetMapping("/{id}/history")
    @Operation(summary = "ดึงประวัติการบริการ / Get customer service history")
    @RateLimit(limit = 60, duration = 60, keyType = "USER_ID")
    public ResponseEntity<List<CustomerHistoryResponseDTO>> getCustomerHistory(@PathVariable UUID id) throws SystemGlobalException {
        List<CustomerHistoryResponseDTO> result = getCustomerHistoryUseCase.execute(id);
        return ResponseEntity.ok(result);
    }

    /*
        ค้นหาลูกค้าจากเบอร์โทร / Get customer by phone
        Rate Limit: อนุญาต 30 ครั้งต่อ 1 นาที / Allows 30 attempts per 1 minute.
    */
    @GetMapping("/phone/{phone}")
    @Operation(summary = "ค้นหาลูกค้าจากเบอร์โทร / Get customer by phone")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    public ResponseEntity<CustomerResponseDTO> getCustomerByPhone(@PathVariable String phone) throws SystemGlobalException {
        CustomerResponseDTO result = getCustomerByPhoneUseCase.execute(phone);
        return ResponseEntity.ok(result);
    }
}
