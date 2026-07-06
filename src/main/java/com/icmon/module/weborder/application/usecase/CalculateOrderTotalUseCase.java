package com.icmon.module.weborder.application.usecase;

import com.icmon.module.weborder.domain.TWebOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CalculateOrderTotalUseCase {

    public Map<String, BigDecimal> execute(TWebOrder order) {
        order.calculateTotal();
        return Map.of(
                "subtotal", order.getSubtotal(),
                "discount", order.getDiscount() != null ? order.getDiscount() : BigDecimal.ZERO,
                "tax", order.getTax() != null ? order.getTax() : BigDecimal.ZERO,
                "shippingCost", order.getShippingCost() != null ? order.getShippingCost() : BigDecimal.ZERO,
                "total", order.getTotal()
        );
    }
}
