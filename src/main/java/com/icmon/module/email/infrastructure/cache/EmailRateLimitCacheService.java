package com.icmon.module.email.infrastructure.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class EmailRateLimitCacheService {

    private final RedisTemplate<String, String> redisTemplate;

    public EmailRateLimitCacheService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean canSendEmail(String userId) {
        String key = "email_rate:" + userId;
        String countStr = redisTemplate.opsForValue().get(key);
        int count = countStr == null ? 0 : Integer.parseInt(countStr);

        if (count >= 50) {
            return false;
        }

        redisTemplate.opsForValue().increment(key);
        if (count == 0) {
            redisTemplate.expire(key, Duration.ofHours(1));
        }
        return true;
    }

    public void resetRateLimit(String userId) {
        redisTemplate.delete("email_rate:" + userId);
    }
}
