package com.icmon.module.job.domain.valueobjects;

import lombok.Value;

@Value
public class Mileage {
    int value;

    public Mileage(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Mileage cannot be negative");
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value + " km";
    }
}
