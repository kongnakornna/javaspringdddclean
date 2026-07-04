package com.icmon.module.auth.infrastructure.cache;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    /*
        ฟังก์ชันนี้กำหนดค่า Redis Cache Manager สำหรับเก็บข้อมูลชั่วคราว เช่น Session, Permission, Token Blacklist
        This function configures the Redis Cache Manager to store temporary data like sessions, permissions, and token blacklists.
    */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // กำหนดรูปแบบการ Serialize เป็น JSON เพื่อให้อ่านค่าได้ง่าย / Define JSON serialization for readability.
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        // สร้าง Cache Manager แยกตามชื่อ Cache ที่มี TTL ต่างกัน / Create separate cache managers with different TTLs.
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .withCacheConfiguration("user-permissions", config.entryTtl(Duration.ofHours(1)))
                .withCacheConfiguration("user-sessions", config.entryTtl(Duration.ofMinutes(15)))
                .withCacheConfiguration("token-blacklist", config.entryTtl(Duration.ofDays(1)))
                .build();
    }
}
