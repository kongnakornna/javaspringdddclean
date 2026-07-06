package com.icmon.module.weborder.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.weborder.domain.enums.PromotionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class MPromotion extends GenericBusinessClass {

    private String promotionCode;
    private String promotionName;
    private PromotionType promotionType;
    private BigDecimal discountValue;
    private BigDecimal minOrderAmount;
    private BigDecimal maxDiscount;
    private String applicableTo;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer usageLimit;
    private Integer usedCount;
    private Boolean isActive;
    private String description;

    public boolean isValid() {
        LocalDateTime now = LocalDateTime.now();
        return isActive != null && isActive
                && now.isAfter(startDate)
                && now.isBefore(endDate)
                && (usageLimit == null || usageLimit <= 0 || usedCount < usageLimit);
    }
}
