package com.icmon.module.customer.infrastructure.cache;

import com.icmon.module.customer.domain.MCustomer;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerCacheService {

    @Cacheable(value = "customers", key = "#customerId")
    public MCustomer getCustomer(UUID customerId) {
        return null;
    }

    @Cacheable(value = "customer_phone", key = "#phone")
    public MCustomer getCustomerByPhone(String phone) {
        return null;
    }

    @CachePut(value = "customers", key = "#customer.id")
    public MCustomer saveCustomer(MCustomer customer) {
        return customer;
    }

    @CacheEvict(value = {"customers", "customer_phone"}, key = "#customerId")
    public void evictCustomer(UUID customerId) {
    }

    @CacheEvict(value = {"customers", "customer_phone"}, allEntries = true)
    public void evictAllCustomers() {
    }
}
