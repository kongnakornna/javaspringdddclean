package com.icmon.module.purchase.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Getter
@Setter
public class TPurchaseOrderDetail extends GenericBusinessClass {

    private UUID poHeaderId;
    private UUID partId;
    private Integer quantityOrdered;
    private Integer quantityReceived;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private BigDecimal discount;
    private BigDecimal netPrice;
    private String note;

    public void calculatePrices() {
        int qty = quantityOrdered != null ? quantityOrdered : 1;
        this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(qty))
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal disc = discount != null ? discount : BigDecimal.ZERO;
        this.netPrice = totalPrice.subtract(disc).setScale(2, RoundingMode.HALF_UP);
    }
}
