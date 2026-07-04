package com.icmon.module.auth.infrastructure.ratelimit;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;

@Component
public class RateLimiterInterceptor implements HandlerInterceptor {

    private final RedisTemplate<String, String> redisTemplate;

    public RateLimiterInterceptor(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /*
        ฟังก์ชันนี้จะถูกเรียกก่อนที่ Controller จะประมวลผล เพื่อตรวจสอบว่า Client เรียก API เกินที่กำหนดหรือไม่
        This function is called before controller execution to check if the client has exceeded the allowed request rate.
    */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        RateLimit rateLimit = handlerMethod.getMethodAnnotation(RateLimit.class);
        if (rateLimit == null) {
            return true; // ไม่มีแอนโนเทชัน ข้าม / No annotation, skip check.
        }

        // ดึง Client ID (IP หรือ User ID) / Retrieve client identifier (IP or User ID).
        String clientId = getClientId(request, rateLimit.keyType());
        // สร้าง Redis Key เฉพาะ path + method / Create unique Redis key for the endpoint.
        String key = "rate:" + clientId + ":" + request.getRequestURI() + ":" + request.getMethod();

        // ดึงจำนวนครั้งที่เรียกแล้ว / Get current request count.
        String countStr = redisTemplate.opsForValue().get(key);
        int count = countStr == null ? 0 : Integer.parseInt(countStr);

        // ถ้าเกิน Limit / If limit exceeded.
        if (count >= rateLimit.limit()) {
            // โยน Exception เพื่อให้ GlobalExceptionHandler จัดการ / Throw exception for handler to manage.
            throw new RateLimitExceededException("Request limit exceeded. Please try again later.");
        }

        // เพิ่มจำนวนครั้งและตั้ง TTL / Increment count and set TTL.
        redisTemplate.opsForValue().increment(key);
        // ถ้าเป็นครั้งแรก ให้ตั้งเวลา TTL / If first request, set TTL.
        if (count == 0) {
            redisTemplate.expire(key, Duration.ofSeconds(rateLimit.duration()));
        }

        return true;
    }

    // ดึง Client ID ตามประเภท / Resolve client ID based on type.
    private String getClientId(HttpServletRequest request, String keyType) {
        if ("USER_ID".equalsIgnoreCase(keyType)) {
            // กรณีต้องใช้ User ID ให้ดึงจาก Header หรือ Security Context / Use User ID from header/context.
            return request.getHeader("X-User-Id") != null ? request.getHeader("X-User-Id") : "anonymous";
        }
        // ค่าเริ่มต้นคือ IP Address / Default to IP Address.
        return request.getRemoteAddr();
    }
}
