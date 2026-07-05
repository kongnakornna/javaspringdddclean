package com.icmon.module.customer.infrastructure.repository;

import com.icmon.module.customer.infrastructure.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {
    Optional<CustomerEntity> findByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, UUID id);

    @Query("SELECT c FROM CustomerEntity c WHERE c.deleted = false " +
           "AND (:name IS NULL OR LOWER(c.fullName) LIKE LOWER(CONCAT('%', :name, '%'))) " +
           "AND (:phone IS NULL OR c.phoneNumber LIKE CONCAT('%', :phone, '%')) " +
           "AND (:type IS NULL OR c.customerType = :type) " +
           "AND (:status IS NULL OR c.status = :status)")
    Page<CustomerEntity> searchCustomers(
            @Param("name") String name,
            @Param("phone") String phone,
            @Param("type") String type,
            @Param("status") String status,
            Pageable pageable);
}
