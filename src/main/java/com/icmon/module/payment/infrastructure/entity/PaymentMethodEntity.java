package com.icmon.module.payment.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "m_payment_method")
@Getter
@Setter
public class PaymentMethodEntity extends GenericBusinessEntity {

    @Column(name = "method_code", unique = true, nullable = false, length = 20)
    private String methodCode;

    @Column(name = "method_name", nullable = false, length = 100)
    private String methodName;

    @Column(name = "method_name_en", length = 100)
    private String methodNameEn;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "requires_approval")
    private boolean requiresApproval;

    @Column(name = "fee_percentage", precision = 5, scale = 2)
    private BigDecimal feePercentage;

    @Column(name = "fee_fixed", precision = 15, scale = 2)
    private BigDecimal feeFixed;

    @Column(columnDefinition = "TEXT")
    private String description;
}
