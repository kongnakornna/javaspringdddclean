package com.icmon.module.auth.infrastructure.security;

import com.icmon.module.auth.infrastructure.entity.UserEntity;
import com.icmon.module.auth.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /*
        ฟังก์ชันนี้โหลดข้อมูลผู้ใช้จากฐานข้อมูลเพื่อใช้ใน Spring Security
        This function loads user data from the database for Spring Security authentication.
    */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + (user.getRole() != null ? user.getRole().name() : "USER"))
        );

        return new User(user.getUsername(), user.getPasswordHash(), authorities);
    }
}
