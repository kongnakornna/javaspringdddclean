package com.icmon.module.auth.application.impl;

import com.icmon.module.auth.application.interfaces.PermissionService;
import com.icmon.module.auth.domain.MPermission;
import com.icmon.module.auth.presentation.dto.request.PermissionRequestDTO;
import com.icmon.module.auth.presentation.dto.response.PermissionResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    @Override
    public List<PermissionResponseDTO> getPermissionsByUserId(UUID userId) throws SystemGlobalException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<MPermission> getPermissionsByRoleId(UUID roleId) throws SystemGlobalException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void assignPermissionToRole(UUID roleId, PermissionRequestDTO dto) throws SystemGlobalException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void revokePermissionFromRole(UUID roleId, UUID permissionId) throws SystemGlobalException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean hasPermission(UUID userId, String permissionName) throws SystemGlobalException {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
