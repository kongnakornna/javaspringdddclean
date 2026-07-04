package com.icmon.module.auth.application.usecase;

import com.icmon.module.auth.application.interfaces.UserService;
import com.icmon.module.auth.presentation.dto.response.UserResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetUserUseCase {
    private final UserService userService;

    public UserResponseDTO execute(UUID userId) throws SystemGlobalException {
        return userService.getUserById(userId);
    }
}
