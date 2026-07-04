package com.icmon.module.auth.presentation.dto.request;

import com.icmon.module.auth.domain.enums.PermissionAction;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class PermissionRequestDTO {

    @NotBlank(message = "Permission name is required")
    private String name;

    private String description;

    private PermissionAction action;

    private String resource;

    private UUID permissionId;
}
