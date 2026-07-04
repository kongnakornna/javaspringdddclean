package com.icmon.module.job.domain.valueobjects;

import lombok.Value;

@Value
public class JobNumber {
    String value;

    public JobNumber(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Job number must not be blank");
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
