package com.icmon.module.customer.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.module.customer.application.interfaces.CustomerService;
import com.icmon.module.customer.domain.MCustomer;
import com.icmon.module.customer.domain.enums.CustomerStatus;
import com.icmon.module.customer.infrastructure.cache.CustomerCacheService;
import com.icmon.module.customer.infrastructure.entity.CarServiceHistoryEntity;
import com.icmon.module.customer.infrastructure.mapper.CustomerMapper;
import com.icmon.module.customer.infrastructure.repository.CarServiceHistoryRepository;
import com.icmon.module.customer.infrastructure.repository.CustomerRepository;
import com.icmon.module.customer.infrastructure.entity.CustomerEntity;
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
public class CustomerServiceImpl extends GenericAuthDomainServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CustomerCacheService cacheService;
    private final CarServiceHistoryRepository carServiceHistoryRepository;

    @Override
    public CustomerResponseDTO createCustomer(CustomerCreateRequestDTO request) throws SystemGlobalException {
        if (customerRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new FailedRequestException("Phone number already registered.", null);
        }
        if (request.getEmail() != null && customerRepository.existsByEmail(request.getEmail())) {
            throw new FailedRequestException("Email already registered.", null);
        }

        CustomerEntity entity = new CustomerEntity();
        entity.setFullName(request.getFullName());
        entity.setDisplayName(request.getDisplayName());
        entity.setPhoneNumber(request.getPhoneNumber());
        entity.setEmail(request.getEmail());
        entity.setCustomerType(request.getCustomerType());
        entity.setStatus(CustomerStatus.ACTIVE);
        entity.setTaxId(request.getTaxId());
        entity.setAddress(request.getAddress());
        entity.setProvince(request.getProvince());
        entity.setCity(request.getCity());
        entity.setDistrict(request.getDistrict());
        entity.setPostalCode(request.getPostalCode());
        entity.setCountry(request.getCountry() != null ? request.getCountry() : "Thailand");
        entity.setContactPerson(request.getContactPerson());
        entity.setContactPhone(request.getContactPhone());
        entity.setNotes(request.getNotes());
        entity.setUserId(getUserId());
        entity.setWhitelabelId(getWhitelabelId());

        CustomerEntity saved = customerRepository.save(entity);
        MCustomer domain = customerMapper.toDomain(saved);
        cacheService.saveCustomer(domain);
        return toResponseDTO(domain);
    }

    @Override
    public CustomerDetailResponseDTO getCustomer(UUID id) throws SystemGlobalException {
        CustomerEntity entity = customerRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Customer not found with id: " + id, null));
        MCustomer domain = customerMapper.toDomain(entity);
        return toDetailResponseDTO(domain);
    }

    @Override
    public Page<CustomerResponseDTO> searchCustomers(CustomerSearchRequestDTO request, Pageable pageable) throws SystemGlobalException {
        Page<CustomerEntity> page = customerRepository.searchCustomers(
                request.getName(), request.getPhone(),
                request.getCustomerType() != null ? request.getCustomerType().name() : null,
                request.getStatus() != null ? request.getStatus().name() : null,
                pageable);
        return page.map(e -> toResponseDTO(customerMapper.toDomain(e)));
    }

    @Override
    public CustomerResponseDTO updateCustomer(UUID id, CustomerUpdateRequestDTO request) throws SystemGlobalException {
        CustomerEntity entity = customerRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Customer not found with id: " + id, null));

        if (request.getFullName() != null) entity.setFullName(request.getFullName());
        if (request.getDisplayName() != null) entity.setDisplayName(request.getDisplayName());
        if (request.getEmail() != null) entity.setEmail(request.getEmail());
        if (request.getPhoneNumber() != null) entity.setPhoneNumber(request.getPhoneNumber());
        if (request.getSecondaryPhone() != null) entity.setSecondaryPhone(request.getSecondaryPhone());
        if (request.getTaxId() != null) entity.setTaxId(request.getTaxId());
        if (request.getAddress() != null) entity.setAddress(request.getAddress());
        if (request.getProvince() != null) entity.setProvince(request.getProvince());
        if (request.getCity() != null) entity.setCity(request.getCity());
        if (request.getDistrict() != null) entity.setDistrict(request.getDistrict());
        if (request.getPostalCode() != null) entity.setPostalCode(request.getPostalCode());
        if (request.getCountry() != null) entity.setCountry(request.getCountry());
        if (request.getContactPerson() != null) entity.setContactPerson(request.getContactPerson());
        if (request.getContactPhone() != null) entity.setContactPhone(request.getContactPhone());
        if (request.getNotes() != null) entity.setNotes(request.getNotes());
        if (request.getStatus() != null) entity.setStatus(request.getStatus());
        if (request.getCustomerType() != null) entity.setCustomerType(request.getCustomerType());

        CustomerEntity saved = customerRepository.save(entity);
        MCustomer domain = customerMapper.toDomain(saved);
        cacheService.saveCustomer(domain);
        return toResponseDTO(domain);
    }

    @Override
    public void deleteCustomer(UUID id) throws SystemGlobalException {
        CustomerEntity entity = customerRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Customer not found with id: " + id, null));
        entity.setDeleted(true);
        entity.setDeletedAt(java.time.LocalDateTime.now());
        customerRepository.save(entity);
        cacheService.evictCustomer(id);
    }

    @Override
    public List<CustomerHistoryResponseDTO> getCustomerHistory(UUID id) throws SystemGlobalException {
        customerRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Customer not found with id: " + id, null));
        List<CarServiceHistoryEntity> histories = carServiceHistoryRepository.findAll().stream()
                .filter(h -> true)
                .collect(Collectors.toList());
        return histories.stream()
                .map(h -> CustomerHistoryResponseDTO.builder()
                        .id(h.getId())
                        .carId(h.getCarId())
                        .jobId(h.getJobId())
                        .serviceDate(h.getServiceDate())
                        .serviceType(h.getServiceType())
                        .description(h.getDescription())
                        .totalCost(h.getTotalCost())
                        .mechanicName(h.getMechanicName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponseDTO getCustomerByPhone(String phone) throws SystemGlobalException {
        MCustomer cached = cacheService.getCustomerByPhone(phone);
        if (cached != null) return toResponseDTO(cached);

        CustomerEntity entity = customerRepository.findByPhoneNumber(phone)
                .orElseThrow(() -> new FailedRequestException("Customer not found with phone: " + phone, null));
        MCustomer domain = customerMapper.toDomain(entity);
        cacheService.saveCustomer(domain);
        return toResponseDTO(domain);
    }

    private CustomerResponseDTO toResponseDTO(MCustomer domain) {
        return CustomerResponseDTO.builder()
                .id(domain.getId())
                .customerCode(domain.getCustomerCode())
                .fullName(domain.getFullName())
                .displayName(domain.getDisplayName())
                .customerType(domain.getCustomerType())
                .status(domain.getStatus())
                .email(domain.getEmail())
                .phoneNumber(domain.getPhoneNumber())
                .address(domain.getAddress())
                .province(domain.getProvince())
                .city(domain.getCity())
                .createdAt(domain.getCreatedAt())
                .build();
    }

    private CustomerDetailResponseDTO toDetailResponseDTO(MCustomer domain) {
        return CustomerDetailResponseDTO.builder()
                .id(domain.getId())
                .customerCode(domain.getCustomerCode())
                .fullName(domain.getFullName())
                .displayName(domain.getDisplayName())
                .customerType(domain.getCustomerType())
                .status(domain.getStatus())
                .taxId(domain.getTaxId())
                .email(domain.getEmail())
                .phoneNumber(domain.getPhoneNumber())
                .secondaryPhone(domain.getSecondaryPhone())
                .address(domain.getAddress())
                .province(domain.getProvince())
                .city(domain.getCity())
                .district(domain.getDistrict())
                .postalCode(domain.getPostalCode())
                .country(domain.getCountry())
                .contactPerson(domain.getContactPerson())
                .contactPhone(domain.getContactPhone())
                .notes(domain.getNotes())
                .lastVisitDate(domain.getLastVisitDate())
                .totalVisitCount(domain.getTotalVisitCount())
                .totalSpent(domain.getTotalSpent())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
