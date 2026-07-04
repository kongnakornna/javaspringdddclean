package com.icmon.module.auth.application.usecase;

import com.icmon.module.auth.application.interfaces.AuthService;
import com.icmon.module.auth.presentation.dto.response.LoginResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenUseCase {
    private final AuthService authService;

    public LoginResponseDTO execute(String refreshToken) throws SystemGlobalException {
        return authService.refreshToken(refreshToken);
    }
}
