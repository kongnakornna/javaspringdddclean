package com.icmon.module.quotation.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "t_quotation_service")
@EqualsAndHashCode(callSuper = true)
public class QuotationServiceEntity extends GenericBusinessEntity {

    @Column(name = "quotation_id", nullable = false)
    private UUID quotationId;

    @Column(name = "service_id", nullable = false)
    private UUID serviceId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", precision = 15, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "total_price", precision = 15, scale = 2)
    private BigDecimal totalPrice;

    @Column(precision = 15, scale = 2)
    private BigDecimal discount;

    @Column(name = "net_price", precision = 15, scale = 2)
    private BigDecimal netPrice;

    @Column(columnDefinition = "TEXT")
    private String note;
}
