package com.icmon.module.weborder.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class TShoppingCart extends GenericBusinessClass {

    private String cartId;
    private UUID customerId;
    private LocalDateTime expiresAt;
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal tax;
    private BigDecimal shipping;
    private BigDecimal total;
    private String promotionCode;
    private String notes;

    private List<TShoppingCartItem> items = new ArrayList<>();

    public void calculateTotals() {
        this.subtotal = items.stream()
                .map(TShoppingCartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.total = this.subtotal
                .add(this.tax != null ? this.tax : BigDecimal.ZERO)
                .add(this.shipping != null ? this.shipping : BigDecimal.ZERO)
                .subtract(this.discount != null ? this.discount : BigDecimal.ZERO);
    }
}
