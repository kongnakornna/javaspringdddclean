package com.icmon.module.auth.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.auth.domain.enums.RoleType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MRole extends GenericBusinessClass {
    private String name;
    private String description;
    private RoleType roleType;
}
