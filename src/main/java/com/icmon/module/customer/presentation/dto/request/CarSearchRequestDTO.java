package com.icmon.module.customer.presentation.dto.request;

import lombok.Data;

@Data
public class CarSearchRequestDTO {
    private String brand;
    private String model;
    private Integer year;
}
