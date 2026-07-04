package com.icmon.module.auth.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import com.icmon.module.auth.domain.enums.PermissionAction;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "m_permission")
@EqualsAndHashCode(callSuper = true)
public class PermissionEntity extends GenericBusinessEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private PermissionAction action;

    @Column(name = "resource")
    private String resource;
}
