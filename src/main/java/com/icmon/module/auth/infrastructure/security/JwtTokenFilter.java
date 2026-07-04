package com.icmon.module.auth.infrastructure.security;

import com.icmon.module.auth.infrastructure.cache.TokenCacheService;
import com.icmon.module.auth.infrastructure.cache.UserPermissionCacheService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenCacheService tokenCacheService;
    private final UserPermissionCacheService userPermissionCacheService;
    private final CustomUserDetailsService userDetailsService;

    /*
        ฟังก์ชันนี้จะทำงานทุกครั้งที่มี Request เข้ามา โดยจะตรวจสอบ JWT Token, ดึงข้อมูลผู้ใช้ และตั้งค่า Context
        This function executes on every incoming request. It validates the JWT, extracts user data, and sets the security context.
    */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 1. ดึง Token จาก Header / Extract Token from Authorization header.
        String token = getBearerToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 2. ดึง User ID จาก Token / Extract User ID from token.
            UUID userId = jwtTokenProvider.getUserIdFromToken(token);

            // 3. ตรวจสอบว่า Token นี้ถูกเพิกถอนหรือไม่ (จาก Redis) / Check if token is revoked (via Redis).
            if (tokenCacheService.isTokenRevoked(token)) {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token has been revoked.");
                return;
            }

            // 4. โหลดสิทธิ์ของผู้ใช้จาก Cache (Redis) / Load user permissions from Cache (Redis).
            userPermissionCacheService.getPermissions(userId);

            // 5. ตั้งค่า MDC (Mapped Diagnostic Context) สำหรับ Logging / Set MDC for logging context.
            MDC.put("userId", userId.toString());
            String whitelabelId = jwtTokenProvider.getWhitelabelIdFromToken(token);
            if (whitelabelId != null) {
                MDC.put("whitelabelId", whitelabelId);
            }
            MDC.put("requestId", UUID.randomUUID().toString());

            // 6. ตั้งค่า SecurityContext / Set SecurityContext.
            String username = jwtTokenProvider.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 7. ส่งต่อ Request ไปยัง Filter ถัดไป / Continue the filter chain.
        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

    private String getBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\":\"" + message + "\"}");
    }
}
