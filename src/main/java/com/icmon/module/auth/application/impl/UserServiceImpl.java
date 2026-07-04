package com.icmon.module.auth.application.impl;

import com.icmon.module.auth.application.interfaces.UserService;
import com.icmon.module.auth.domain.MUser;
import com.icmon.module.auth.presentation.dto.request.UserCreateDTO;
import com.icmon.module.auth.presentation.dto.request.UserUpdateDTO;
import com.icmon.module.auth.presentation.dto.response.UserResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public UserResponseDTO createUser(UserCreateDTO dto) throws SystemGlobalException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public UserResponseDTO updateUser(UUID userId, UserUpdateDTO dto) throws SystemGlobalException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void deleteUser(UUID userId) throws SystemGlobalException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public UserResponseDTO getUserById(UUID userId) throws SystemGlobalException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<UserResponseDTO> getAllUsers() throws SystemGlobalException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public MUser findByUsername(String username) throws SystemGlobalException {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
