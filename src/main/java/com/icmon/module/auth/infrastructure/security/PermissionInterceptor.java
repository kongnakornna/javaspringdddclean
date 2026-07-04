package com.icmon.module.auth.infrastructure.security;

import com.icmon.module.auth.application.interfaces.PermissionService;
import com.icmon.module.auth.infrastructure.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PermissionInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final PermissionService permissionService;

    /*
        ฟังก์ชันนี้ตรวจสอบสิทธิ์ของผู้ใช้ก่อนที่ Controller จะประมวลผล Request
        This function checks user permissions before the controller processes the request.
    */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String bearerToken = request.getHeader("Authorization");
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            return true; // JwtTokenFilter จัดการ auth แล้ว / Auth is handled by JwtTokenFilter.
        }

        String token = bearerToken.substring(7);
        if (!jwtTokenProvider.validateToken(token)) {
            return true;
        }

        UUID userId = jwtTokenProvider.getUserIdFromToken(token);
        // สามารถเพิ่มการตรวจสอบ Permission เฉพาะ Endpoint ได้ที่นี่
        // Additional endpoint-level permission checks can be added here.

        return true;
    }
}
