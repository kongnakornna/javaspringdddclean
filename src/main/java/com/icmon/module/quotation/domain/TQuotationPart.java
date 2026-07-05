package com.icmon.module.quotation.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.UUID;

/*
    โดเมนรายการอะไหล่ในใบเสนอราคา / Quotation part domain entity.
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class TQuotationPart extends GenericBusinessClass {

    private UUID quotationId;
    private UUID partId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private BigDecimal discount;
    private BigDecimal netPrice;
    private String note;

    /*
        ฟังก์ชันนี้คำนวณราคารวมและราคาสุทธิของรายการอะไหล่
        This function calculates total and net prices for the part item.
    */
    public void calculatePrices() {
        if (quantity == null) quantity = 1;
        BigDecimal qty = BigDecimal.valueOf(quantity);
        this.totalPrice = this.unitPrice.multiply(qty);
        this.netPrice = this.totalPrice.subtract(this.discount != null ? this.discount : BigDecimal.ZERO);
    }
}
