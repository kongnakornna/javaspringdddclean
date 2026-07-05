package com.icmon.module.customer.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.customer.domain.enums.CustomerStatus;
import com.icmon.module.customer.domain.enums.CustomerType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class MCustomer extends GenericBusinessClass {
    private String customerCode;
    private String fullName;
    private String displayName;
    private CustomerType customerType;
    private CustomerStatus status;
    private String taxId;
    private String email;
    private String phoneNumber;
    private String secondaryPhone;
    private String address;
    private String province;
    private String city;
    private String district;
    private String postalCode;
    private String country;
    private String contactPerson;
    private String contactPhone;
    private String notes;
    private LocalDateTime lastVisitDate;
    private Integer totalVisitCount;
    private BigDecimal totalSpent;

    public void recordVisit(BigDecimal spentAmount) {
        this.lastVisitDate = LocalDateTime.now();
        this.totalVisitCount = (this.totalVisitCount != null ? this.totalVisitCount : 0) + 1;
        this.totalSpent = (this.totalSpent != null ? this.totalSpent : BigDecimal.ZERO).add(spentAmount);
    }

    public boolean canReceiveService() {
        return this.status == CustomerStatus.ACTIVE;
    }

    public void blacklist(String reason) {
        this.status = CustomerStatus.BLACKLISTED;
        this.notes = (this.notes != null ? this.notes + "\n" : "") + "Blacklisted: " + reason;
    }
}
