package com.icmon.module.weborder.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class MSalesPrice extends GenericBusinessClass {

    private UUID itemId;
    private String priceTier;
    private BigDecimal unitPrice;
    private String currency;
    private LocalDateTime effectiveDate;
    private LocalDateTime expiryDate;
    private Integer minQuantity;
    private Boolean isActive;
}
