package com.icmon.module.auth.application.usecase;

import com.icmon.module.auth.application.interfaces.UserService;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteUserUseCase {
    private final UserService userService;

    public void execute(UUID userId) throws SystemGlobalException {
        userService.deleteUser(userId);
    }
}
