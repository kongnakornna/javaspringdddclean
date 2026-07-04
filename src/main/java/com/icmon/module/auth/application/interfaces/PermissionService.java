package com.icmon.module.auth.application.interfaces;

import com.icmon.module.auth.domain.MPermission;
import com.icmon.module.auth.presentation.dto.request.PermissionRequestDTO;
import com.icmon.module.auth.presentation.dto.response.PermissionResponseDTO;
import com.icmon.exception.SystemGlobalException;

import java.util.List;
import java.util.UUID;

public interface PermissionService {
    List<PermissionResponseDTO> getPermissionsByUserId(UUID userId) throws SystemGlobalException;
    List<MPermission> getPermissionsByRoleId(UUID roleId) throws SystemGlobalException;
    void assignPermissionToRole(UUID roleId, PermissionRequestDTO dto) throws SystemGlobalException;
    void revokePermissionFromRole(UUID roleId, UUID permissionId) throws SystemGlobalException;
    boolean hasPermission(UUID userId, String permissionName) throws SystemGlobalException;
}
