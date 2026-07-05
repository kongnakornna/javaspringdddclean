package com.icmon.module.auth.presentation.dto.request;

import com.icmon.module.auth.domain.enums.PermissionAction;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "ข้อมูลคำขอสิทธิ์การเข้าถึง / Permission request")
public class PermissionRequestDTO {

    @NotBlank(message = "Permission name is required")
    @Schema(description = "ชื่อสิทธิ์ / Permission name", example = "USER_READ", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "คำอธิบาย / Description", example = "Read user information")
    private String description;

    @Schema(description = "การดำเนินการ / Action", example = "READ")
    private PermissionAction action;

    @Schema(description = "ทรัพยากร / Resource", example = "USER")
    private String resource;

    @Schema(description = "รหัสสิทธิ์ / Permission ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID permissionId;
}
