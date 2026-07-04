package com.icmon.module.auth.presentation.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private String accessToken;
    private String refreshToken;
    private long expiresIn;
    private String tokenType;
    private UserResponseDTO user;
}
