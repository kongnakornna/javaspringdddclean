package com.git.spring_boot_ddd_template._shared.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class GenericBusinessClass extends GenericClass {
    @JsonIgnore
    private UUID userId;

    @JsonIgnore
    private UUID whitelabelId;
}