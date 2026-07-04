package com.icmon.module.auth.presentation.dto.response;

import com.icmon.module.auth.domain.enums.TokenType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class TokenResponseDTO {
    private UUID id;
    private String token;
    private TokenType tokenType;
    private LocalDateTime expiryDate;
    private boolean revoked;
}
