package com.icmon.module.job.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class TJobPartSales extends GenericBusinessClass {

    private UUID jobId;
    private UUID partId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal discount;
    private String note;

    public BigDecimal getTotalPrice() {
        return unitPrice != null && quantity != null
                ? unitPrice.multiply(BigDecimal.valueOf(quantity))
                : BigDecimal.ZERO;
    }

    public BigDecimal getNetPrice() {
        BigDecimal total = getTotalPrice();
        return discount != null ? total.subtract(discount) : total;
    }
}
