package com.git.spring_boot_ddd_template._shared.infrastructure;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;


@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class GenericBusinessEntity extends GenericEntity {
    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;

    @Column(name = "whiteLabel_id", updatable = false, nullable = false)
    private UUID whitelabelId;
}