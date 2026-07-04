package com.icmon.module.auth.infrastructure.security;

import com.icmon.module.auth.infrastructure.cache.TokenCacheService;
import com.icmon.module.auth.infrastructure.cache.UserPermissionCacheService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Lazy;  
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
// ❌ ลบ @RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenCacheService tokenCacheService;
    private final UserPermissionCacheService userPermissionCacheService;
    private final CustomUserDetailsService userDetailsService;

    // ✅ ใช้ Constructor ที่มี @Lazy
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider,
                          TokenCacheService tokenCacheService,
                          UserPermissionCacheService userPermissionCacheService,
                          @Lazy CustomUserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenCacheService = tokenCacheService;
        this.userPermissionCacheService = userPermissionCacheService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = getBearerToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            UUID userId = jwtTokenProvider.getUserIdFromToken(token);

            if (tokenCacheService.isTokenRevoked(token)) {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token has been revoked.");
                return;
            }

            userPermissionCacheService.getPermissions(userId);

            MDC.put("userId", userId.toString());
            String whitelabelId = jwtTokenProvider.getWhitelabelIdFromToken(token);
            if (whitelabelId != null) {
                MDC.put("whitelabelId", whitelabelId);
            }
            MDC.put("requestId", UUID.randomUUID().toString());

            String username = jwtTokenProvider.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

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