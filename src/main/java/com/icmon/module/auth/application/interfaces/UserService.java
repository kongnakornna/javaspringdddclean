package com.icmon.module.auth.application.interfaces;

import com.icmon.module.auth.domain.MUser;
import com.icmon.module.auth.presentation.dto.request.UserCreateDTO;
import com.icmon.module.auth.presentation.dto.request.UserUpdateDTO;
import com.icmon.module.auth.presentation.dto.response.UserResponseDTO;
import com.icmon.exception.SystemGlobalException;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponseDTO createUser(UserCreateDTO dto) throws SystemGlobalException;
    UserResponseDTO updateUser(UUID userId, UserUpdateDTO dto) throws SystemGlobalException;
    void deleteUser(UUID userId) throws SystemGlobalException;
    UserResponseDTO getUserById(UUID userId) throws SystemGlobalException;
    List<UserResponseDTO> getAllUsers() throws SystemGlobalException;
    MUser findByUsername(String username) throws SystemGlobalException;
}
