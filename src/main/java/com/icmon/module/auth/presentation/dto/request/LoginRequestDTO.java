package com.icmon.module.auth.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "ข้อมูลคำขอเข้าสู่ระบบ / Login request")
public class LoginRequestDTO {

    @NotBlank(message = "Username is required")
    @Schema(description = "ชื่อผู้ใช้ / Username", example = "admin", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank(message = "Password is required")
    @Schema(description = "รหัสผ่าน / Password", example = "P@ssw0rd", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
