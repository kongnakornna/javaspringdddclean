package com.icmon.module.auth.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret:default-secret-key-please-change-in-production}")
    private String jwtSecret;

    @Value("${jwt.expiration:3600000}")
    private long jwtExpiration;

    @Value("${jwt.refresh-expiration:86400000}")
    private long refreshExpiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /*
        ฟังก์ชันนี้สร้าง Access Token สำหรับผู้ใช้ที่ Login สำเร็จ
        This function generates an Access Token for a successfully authenticated user.
    */
    public String generateAccessToken(UUID userId, String username, UUID whitelabelId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("username", username)
                .claim("whitelabelId", whitelabelId != null ? whitelabelId.toString() : null)
                .claim("type", "ACCESS")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /*
        ฟังก์ชันนี้สร้าง Refresh Token สำหรับต่ออายุ Access Token
        This function generates a Refresh Token used to renew the Access Token.
    */
    public String generateRefreshToken(UUID userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("type", "REFRESH")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /*
        ฟังก์ชันนี้ตรวจสอบความถูกต้องของ Token
        This function validates whether the given token is valid.
    */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public UUID getUserIdFromToken(String token) {
        Claims claims = getClaims(token);
        return UUID.fromString(claims.getSubject());
    }

    public String getUsernameFromToken(String token) {
        return getClaims(token).get("username", String.class);
    }

    public String getWhitelabelIdFromToken(String token) {
        return getClaims(token).get("whitelabelId", String.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
