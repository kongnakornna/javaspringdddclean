package com.icmon.module.weborder.domain.valueobjects;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ShippingAddress {
    String addressLine1;
    String addressLine2;
    String district;
    String city;
    String province;
    String postalCode;
    String country;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(addressLine1);
        if (addressLine2 != null && !addressLine2.isBlank()) sb.append(", ").append(addressLine2);
        sb.append(", ").append(district);
        sb.append(", ").append(city);
        sb.append(", ").append(province);
        sb.append(" ").append(postalCode);
        if (country != null && !country.isBlank()) sb.append(", ").append(country);
        return sb.toString();
    }
}
