package com.icmon.configuration.security;

import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.icmon.logging.LogService;
import com.icmon.module.auth.infrastructure.security.CustomUserDetailsService;

import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
@Profile({"dev", "prod"})
public class JwtTokenFilter extends OncePerRequestFilter {

    private final LogService logService;
    private final CustomUserDetailsService customUserDetailsService; 

    @Value("${jwt.privateKey}")
    private String privateKey;

    @Value("${jwt.publicKey}")
    private String publicKey;

    public JwtTokenFilter(LogService logService, 
                            @Lazy CustomUserDetailsService customUserDetailsService) {
            this.logService = logService;
            this.customUserDetailsService = customUserDetailsService;
        }

    //TODO: Extrair Roles do JWT e melhorar o tratamento de requisição.

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws IOException {
        try {
            String jwt = getBearerToken(request);

            if (jwt != null) {
                String requestId = generateUniqueRequestId();

                Jws<Claims> jwsClaims = Jwts.parserBuilder()
                        .setSigningKey(getPublicKeyFromString(publicKey))
                        .build()
                        .parseClaimsJws(jwt);

                Date expiration = jwsClaims.getBody().getExpiration();
                if (expiration != null && expiration.before(new Date())) {
                    throw new ExpiredJwtException(jwsClaims.getHeader(), jwsClaims.getBody(), "Expired token.");
                }

                String userId = jwsClaims.getBody().get("userId", String.class);
                String whitelabelId = jwsClaims.getBody().get("whitelabelId", String.class);

                if (userId != null && whitelabelId != null) {
                    logService.saveRequest( whitelabelId, userId, request.getRequestURI());

                    MDC.put("userId", userId);
                    MDC.put("whitelabelId", whitelabelId);
                    MDC.put("requestId", requestId);
                } else{
                    throw new JwtException("Invalid JWT");
                }
            }

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException ex) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Expired JWT token.");
        } catch (JwtException | ServletException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token.");
        } catch (Exception ex) {
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error.");
        } finally {
            MDC.clear();
        }
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setContentType("application/json");
        response.setStatus(status);
        String json = String.format("{\"error\": \"%s\"}", message);
        response.getWriter().write(json);
    }

    private String getBearerToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private String generateUniqueRequestId() {
        return UUID.randomUUID().toString();
    }

    private Key getPublicKeyFromString(String base64PublicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Base64.getDecoder().decode(base64PublicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }
}