package com.icmon.module.auth.presentation.dto.response;

import com.icmon.module.auth.domain.enums.TokenType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Schema(description = "ข้อมูลตอบกลับโทเค็น / Token response")
public class TokenResponseDTO {
    @Schema(description = "รหัสโทเค็น / Token ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID id;

    @Schema(description = "ค่าโทเค็น / Token value", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String token;

    @Schema(description = "ประเภทโทเค็น / Token type", example = "REFRESH")
    private TokenType tokenType;

    @Schema(description = "วันหมดอายุ / Expiry date", example = "2025-12-31T23:59:59")
    private LocalDateTime expiryDate;

    @Schema(description = "ถูกเพิกถอนหรือไม่ / Whether the token is revoked", example = "false")
    private boolean revoked;
}
