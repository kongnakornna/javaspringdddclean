package com.icmon.module.auth.application.interfaces;

import com.icmon.module.auth.presentation.dto.request.LoginRequestDTO;
import com.icmon.module.auth.presentation.dto.request.RegisterRequestDTO;
import com.icmon.module.auth.presentation.dto.response.LoginResponseDTO;
import com.icmon.module.auth.presentation.dto.response.UserResponseDTO;
import com.icmon.exception.SystemGlobalException;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO request, HttpServletRequest httpRequest) throws SystemGlobalException;
    void logout(String token) throws SystemGlobalException;
    LoginResponseDTO refreshToken(String refreshToken) throws SystemGlobalException;
    boolean validateToken(String token);
    LoginResponseDTO register(RegisterRequestDTO request) throws SystemGlobalException;
    UserResponseDTO getCurrentUser() throws SystemGlobalException;
}
