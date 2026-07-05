package com.icmon.module.customer.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import com.icmon.module.customer.domain.enums.CustomerStatus;
import com.icmon.module.customer.domain.enums.CustomerType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "m_customer")
@EqualsAndHashCode(callSuper = true)
public class CustomerEntity extends GenericBusinessEntity {
    @Column(name = "customer_code", unique = true, nullable = false)
    private String customerCode;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "display_name")
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type", nullable = false)
    private CustomerType customerType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerStatus status;

    @Column(name = "tax_id")
    private String taxId;

    @Column(length = 100)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "secondary_phone")
    private String secondaryPhone;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(length = 100)
    private String province;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String district;

    @Column(name = "postal_code", length = 10)
    private String postalCode;

    @Column(length = 50)
    private String country;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "last_visit_date")
    private LocalDateTime lastVisitDate;

    @Column(name = "total_visit_count")
    private Integer totalVisitCount;

    @Column(name = "total_spent", precision = 15, scale = 2)
    private BigDecimal totalSpent;
}
