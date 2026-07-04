package com.icmon.module.auth.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.auth.domain.enums.PermissionAction;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MPermission extends GenericBusinessClass {
    // ชื่อสิทธิ์ เช่น "JOB_CREATE", "INVENTORY_READ"
    private String name;
    private String description;
    private PermissionAction action;
    // ทรัพยากร เช่น JOB, INVENTORY, USER, REPORT
    private String resource;
}
