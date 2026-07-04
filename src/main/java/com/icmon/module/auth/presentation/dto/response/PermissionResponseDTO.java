package com.icmon.module.auth.presentation.dto.response;

import com.icmon.module.auth.domain.enums.PermissionAction;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PermissionResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private PermissionAction action;
    private String resource;
}
