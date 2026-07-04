package com.icmon.module.auth.application.usecase;

import com.icmon.module.auth.application.interfaces.AuthService;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogoutUseCase {
    private final AuthService authService;

    public void execute(String token) throws SystemGlobalException {
        authService.logout(token);
    }
}
