package com.icmon.module.customer.application.interfaces;

import com.icmon.module.customer.presentation.dto.request.*;
import com.icmon.module.customer.presentation.dto.response.*;
import com.icmon.exception.SystemGlobalException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    CustomerResponseDTO createCustomer(CustomerCreateRequestDTO request) throws SystemGlobalException;
    CustomerDetailResponseDTO getCustomer(UUID id) throws SystemGlobalException;
    Page<CustomerResponseDTO> searchCustomers(CustomerSearchRequestDTO request, Pageable pageable) throws SystemGlobalException;
    CustomerResponseDTO updateCustomer(UUID id, CustomerUpdateRequestDTO request) throws SystemGlobalException;
    void deleteCustomer(UUID id) throws SystemGlobalException;
    List<CustomerHistoryResponseDTO> getCustomerHistory(UUID id) throws SystemGlobalException;
    CustomerResponseDTO getCustomerByPhone(String phone) throws SystemGlobalException;
}
