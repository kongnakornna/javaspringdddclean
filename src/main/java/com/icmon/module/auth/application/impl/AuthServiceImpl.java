package com.icmon.module.auth.application.impl;

import com.icmon.module.auth.application.interfaces.AuthService;
import com.icmon.module.auth.domain.MUser;
import com.icmon.module.auth.domain.enums.RoleType;
import com.icmon.module.auth.domain.enums.TokenType;
import com.icmon.module.auth.domain.enums.UserStatus;
import com.icmon.module.auth.domain.valueobjects.Email;
import com.icmon.module.auth.infrastructure.entity.UserEntity;
import com.icmon.module.auth.infrastructure.entity.UserTokenEntity;
import com.icmon.module.auth.infrastructure.mapper.UserMapper;
import com.icmon.module.auth.infrastructure.repository.UserRepository;
import com.icmon.module.auth.infrastructure.repository.UserTokenRepository;
import com.icmon.module.auth.infrastructure.security.JwtTokenProvider;
import com.icmon.module.auth.presentation.dto.request.LoginRequestDTO;
import com.icmon.module.auth.presentation.dto.request.RegisterRequestDTO;
import com.icmon.module.auth.presentation.dto.response.LoginResponseDTO;
import com.icmon.module.auth.presentation.dto.response.UserResponseDTO;
import com.icmon.exception.SystemGlobalException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public LoginResponseDTO register(RegisterRequestDTO request) throws SystemGlobalException {
        log.info("Registering new user: username={}, email={}", request.getUsername(), request.getEmail());

        if (userRepository.existsByUsername(request.getUsername())) {
            log.warn("Registration failed - username already exists: {}", request.getUsername());
            throw new SystemGlobalException("Username already exists", null);
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Registration failed - email already exists: {}", request.getEmail());
            throw new SystemGlobalException("Email already exists", null);
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        MUser mUser = new MUser();
        mUser.setUsername(request.getUsername());
        mUser.setEmail(new Email(request.getEmail()));
        mUser.setPasswordHash(hashedPassword);
        mUser.setFullName(request.getFullName());
        mUser.setPhoneNumber(request.getPhoneNumber());
        mUser.setStatus(UserStatus.ACTIVE);
        mUser.setRole(RoleType.USER);

        UserEntity userEntity = userMapper.toEntity(mUser);
        userEntity.setUserId(UUID.randomUUID());
        userEntity.setWhitelabelId(UUID.randomUUID());

        userRepository.save(userEntity);

        UUID newId = userEntity.getId();

        String accessToken = jwtTokenProvider.generateAccessToken(newId, request.getUsername(), newId);
        String refreshToken = jwtTokenProvider.generateRefreshToken(newId);

        saveToken(newId, accessToken, TokenType.ACCESS, LocalDateTime.now().plusHours(1));
        saveToken(newId, refreshToken, TokenType.REFRESH, LocalDateTime.now().plusDays(1));

        log.info("User registered successfully: id={}, username={}", newId, request.getUsername());

        UserResponseDTO userResponse = UserResponseDTO.builder()
                .id(newId)
                .username(request.getUsername())
                .email(request.getEmail())
                .fullName(request.getFullName())
                .status(UserStatus.ACTIVE)
                .phoneNumber(request.getPhoneNumber())
                .role(RoleType.USER)
                .build();

        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(3600)
                .tokenType("Bearer")
                .user(userResponse)
                .build();
    }

    @Override
    @Transactional
    public LoginResponseDTO login(LoginRequestDTO request, HttpServletRequest httpRequest) throws SystemGlobalException {
        log.info("Authenticating user: username={}", request.getUsername());

        UserEntity userEntity = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new SystemGlobalException("Invalid username or password", null));

        if (!passwordEncoder.matches(request.getPassword(), userEntity.getPasswordHash())) {
            log.warn("Login failed - invalid password: username={}", request.getUsername());
            throw new SystemGlobalException("Invalid username or password", null);
        }

        UUID userPkId = userEntity.getId();

        String accessToken = jwtTokenProvider.generateAccessToken(userPkId, request.getUsername(), userPkId);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userPkId);

        saveToken(userPkId, accessToken, TokenType.ACCESS, LocalDateTime.now().plusHours(1));
        saveToken(userPkId, refreshToken, TokenType.REFRESH, LocalDateTime.now().plusDays(1));

        log.info("User logged in successfully: id={}, username={}", userPkId, request.getUsername());

        UserResponseDTO userResponse = UserResponseDTO.builder()
                .id(userPkId)
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .fullName(userEntity.getFullName())
                .status(userEntity.getStatus())
                .phoneNumber(userEntity.getPhoneNumber())
                .role(userEntity.getRole())
                .build();

        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(3600)
                .tokenType("Bearer")
                .user(userResponse)
                .build();
    }

    @Override
    public void logout(String token) throws SystemGlobalException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public LoginResponseDTO refreshToken(String refreshToken) throws SystemGlobalException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    @Override
    public UserResponseDTO getCurrentUser() throws SystemGlobalException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            log.warn("Unauthenticated access to getCurrentUser");
            throw new SystemGlobalException("User is not authenticated", null);
        }

        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new SystemGlobalException("User not found", null));

        log.info("Fetched current user profile: id={}, username={}", userEntity.getId(), username);

        return UserResponseDTO.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .fullName(userEntity.getFullName())
                .status(userEntity.getStatus())
                .phoneNumber(userEntity.getPhoneNumber())
                .profileImageUrl(userEntity.getProfileImageUrl())
                .role(userEntity.getRole())
                .build();
    }

    private void saveToken(UUID userId, String token, TokenType tokenType, LocalDateTime expiryDate) {
        UserTokenEntity tokenEntity = new UserTokenEntity();
        tokenEntity.setUserId(userId);
        tokenEntity.setToken(token);
        tokenEntity.setTokenType(tokenType);
        tokenEntity.setExpiryDate(expiryDate);
        userTokenRepository.save(tokenEntity);
    }
}
