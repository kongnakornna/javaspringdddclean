package com.icmon.configuration.web;

import com.icmon.module.auth.infrastructure.ratelimit.RateLimiterInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RateLimiterInterceptor rateLimiterInterceptor;

    public WebConfig(RateLimiterInterceptor rateLimiterInterceptor) {
        this.rateLimiterInterceptor = rateLimiterInterceptor;
    }

    /*
        ฟังก์ชันนี้ลงทะเบียน Interceptor เพื่อให้ทำงานกับทุก Request ที่เข้ามาในระบบ
        This function registers the interceptor to handle all incoming requests.
    */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimiterInterceptor)
                .addPathPatterns("/api/v1/**")
                .excludePathPatterns(
                        "/api/v1/auth/login",
                        "/api/v1/auth/register",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                );
    }
}
