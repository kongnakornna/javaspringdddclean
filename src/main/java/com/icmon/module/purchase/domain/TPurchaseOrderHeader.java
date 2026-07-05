package com.icmon.module.purchase.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.purchase.domain.enums.DiscountType;
import com.icmon.module.purchase.domain.enums.PurchaseOrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class TPurchaseOrderHeader extends GenericBusinessClass {

    private String poNo;
    private UUID quotationId;
    private UUID jobId;
    private UUID supplierId;
    private LocalDateTime poDate;
    private LocalDateTime expectedDeliveryDate;
    private LocalDateTime actualDeliveryDate;
    private PurchaseOrderStatus status;
    private BigDecimal subtotal;
    private BigDecimal taxRate;
    private BigDecimal taxAmount;
    private DiscountType discountType;
    private BigDecimal discountValue;
    private BigDecimal total;
    private String currency;
    private BigDecimal exchangeRate;
    private BigDecimal shippingCost;
    private String paymentTerms;
    private String deliveryAddress;
    private String notes;
    private String termsAndConditions;
    private LocalDateTime sentAt;
    private LocalDateTime confirmedAt;
    private UUID receivedBy;
    private List<TPurchaseOrderDetail> details = new ArrayList<>();
    private List<TPurchaseOrderStatusHistory> statusHistories = new ArrayList<>();

    public void calculateTotals() {
        BigDecimal sub = details.stream()
                .map(TPurchaseOrderDetail::getNetPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.subtotal = sub.setScale(2, RoundingMode.HALF_UP);

        BigDecimal taxAmt = subtotal.multiply(taxRate != null ? taxRate : BigDecimal.valueOf(7))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        this.taxAmount = taxAmt;

        BigDecimal discAmt = BigDecimal.ZERO;
        if (discountType != null && discountValue != null) {
            if (discountType == DiscountType.PERCENTAGE) {
                discAmt = subtotal.multiply(discountValue).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            } else {
                discAmt = discountValue;
            }
        }

        BigDecimal ship = shippingCost != null ? shippingCost : BigDecimal.ZERO;
        this.total = subtotal.add(taxAmt).subtract(discAmt).add(ship).setScale(2, RoundingMode.HALF_UP);
    }
}
