package com.icmon.module.auth.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "ข้อมูลตอบกลับการเข้าสู่ระบบ / Login response")
public class LoginResponseDTO {
    @Schema(description = "โทเค็นสำหรับเข้าถึง / Access token", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String accessToken;

    @Schema(description = "โทเค็นสำหรับรีเฟรช / Refresh token", example = "dGhpcyBpcyBhIHJlZnJlc2g...")
    private String refreshToken;

    @Schema(description = "อายุของโทเค็น (วินาที) / Token expiry in seconds", example = "3600")
    private long expiresIn;

    @Schema(description = "ประเภทโทเค็น / Token type", example = "Bearer")
    private String tokenType;

    @Schema(description = "ข้อมูลผู้ใช้ / User information")
    private UserResponseDTO user;
}
