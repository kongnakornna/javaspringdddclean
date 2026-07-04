package com.icmon.module.auth.application.impl;

import com.icmon.module.auth.application.interfaces.AuthService;
import com.icmon.module.auth.presentation.dto.request.LoginRequestDTO;
import com.icmon.module.auth.presentation.dto.request.RegisterRequestDTO;
import com.icmon.module.auth.presentation.dto.response.LoginResponseDTO;
import com.icmon.module.auth.presentation.dto.response.UserResponseDTO;
import com.icmon.exception.SystemGlobalException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Override
    public LoginResponseDTO login(LoginRequestDTO request, HttpServletRequest httpRequest) throws SystemGlobalException {
        // TODO: implement login logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void logout(String token) throws SystemGlobalException {
        // TODO: implement logout — revoke token
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public LoginResponseDTO refreshToken(String refreshToken) throws SystemGlobalException {
        // TODO: implement token refresh
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean validateToken(String token) {
        // TODO: implement token validation
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public LoginResponseDTO register(RegisterRequestDTO request) throws SystemGlobalException {
        // TODO: implement user registration
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public UserResponseDTO getCurrentUser() throws SystemGlobalException {
        // TODO: implement get current user from security context
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
