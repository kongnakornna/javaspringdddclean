package com.icmon.module.auth.application.usecase;

import com.icmon.module.auth.application.interfaces.AuthService;
import com.icmon.module.auth.presentation.dto.request.LoginRequestDTO;
import com.icmon.module.auth.presentation.dto.response.LoginResponseDTO;
import com.icmon.exception.SystemGlobalException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginUseCase {
    private final AuthService authService;

    public LoginResponseDTO execute(LoginRequestDTO request, HttpServletRequest httpRequest) throws SystemGlobalException {
        return authService.login(request, httpRequest);
    }
}
