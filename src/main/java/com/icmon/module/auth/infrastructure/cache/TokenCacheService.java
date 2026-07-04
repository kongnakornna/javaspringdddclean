package com.icmon.module.auth.infrastructure.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class TokenCacheService {

    private static final String BLACKLIST_PREFIX = "token:blacklist:";

    private final RedisTemplate<String, String> redisTemplate;

    public TokenCacheService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /*
        ฟังก์ชันนี้เพิ่ม Token เข้า Blacklist ใน Redis เมื่อ Logout หรือ Revoke
        This function adds a token to the Redis blacklist on logout or revocation.
    */
    public void blacklistToken(String token, Duration ttl) {
        redisTemplate.opsForValue().set(BLACKLIST_PREFIX + token, "revoked", ttl);
    }

    /*
        ฟังก์ชันนี้ตรวจสอบว่า Token ถูกเพิกถอนหรืออยู่ใน Blacklist หรือไม่
        This function checks whether a token has been revoked or is in the blacklist.
    */
    public boolean isTokenRevoked(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(BLACKLIST_PREFIX + token));
    }
}
