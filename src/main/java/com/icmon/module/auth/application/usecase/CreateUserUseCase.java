package com.icmon.module.auth.application.usecase;

import com.icmon.module.auth.application.interfaces.UserService;
import com.icmon.module.auth.presentation.dto.request.UserCreateDTO;
import com.icmon.module.auth.presentation.dto.response.UserResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserUseCase {
    private final UserService userService;

    public UserResponseDTO execute(UserCreateDTO dto) throws SystemGlobalException {
        return userService.createUser(dto);
    }
}
