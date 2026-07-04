package com.icmon.module.auth.presentation.controller;

import com.icmon.module.auth.application.interfaces.PermissionService;
import com.icmon.module.auth.presentation.dto.request.PermissionRequestDTO;
import com.icmon.module.auth.presentation.dto.response.PermissionResponseDTO;
import com.icmon.exception.SystemGlobalException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/permissions")
@Tag(name = "Permission Management", description = "Permission and Role APIs")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping("/users/{userId}")
    @Operation(summary = "Get permissions by user ID")
    public ResponseEntity<List<PermissionResponseDTO>> getPermissionsByUserId(@PathVariable UUID userId) throws SystemGlobalException {
        return ResponseEntity.ok(permissionService.getPermissionsByUserId(userId));
    }

    @PostMapping("/roles/{roleId}")
    @Operation(summary = "Assign permission to role")
    public ResponseEntity<Void> assignPermissionToRole(@PathVariable UUID roleId,
                                                       @RequestBody PermissionRequestDTO dto) throws SystemGlobalException {
        permissionService.assignPermissionToRole(roleId, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/roles/{roleId}/permissions/{permissionId}")
    @Operation(summary = "Revoke permission from role")
    public ResponseEntity<Void> revokePermissionFromRole(@PathVariable UUID roleId,
                                                         @PathVariable UUID permissionId) throws SystemGlobalException {
        permissionService.revokePermissionFromRole(roleId, permissionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check")
    @Operation(summary = "Check if user has permission")
    public ResponseEntity<Boolean> hasPermission(@RequestParam UUID userId,
                                                 @RequestParam String permissionName) throws SystemGlobalException {
        return ResponseEntity.ok(permissionService.hasPermission(userId, permissionName));
    }
}
