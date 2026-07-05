package com.icmon.module.auth.presentation.dto.response;

import com.icmon.module.auth.domain.enums.PermissionAction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@Schema(description = "ข้อมูลตอบกลับสิทธิ์การเข้าถึง / Permission response")
public class PermissionResponseDTO {
    @Schema(description = "รหัสสิทธิ์ / Permission ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID id;

    @Schema(description = "ชื่อสิทธิ์ / Permission name", example = "USER_READ")
    private String name;

    @Schema(description = "คำอธิบาย / Description", example = "Read user information")
    private String description;

    @Schema(description = "การดำเนินการ / Action", example = "READ")
    private PermissionAction action;

    @Schema(description = "ทรัพยากร / Resource", example = "USER")
    private String resource;
}
