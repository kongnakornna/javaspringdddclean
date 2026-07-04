package com.icmon.module.auth.application.usecase;

import com.icmon.module.auth.application.interfaces.PermissionService;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ValidatePermissionUseCase {
    private final PermissionService permissionService;

    public boolean execute(UUID userId, String permissionName) throws SystemGlobalException {
        return permissionService.hasPermission(userId, permissionName);
    }
}
