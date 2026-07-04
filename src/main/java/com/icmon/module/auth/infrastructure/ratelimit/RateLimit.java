package com.icmon.module.auth.infrastructure.ratelimit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
    แอนโนเทชันนี้ใช้สำหรับกำหนดขีดจำกัดการเรียก API ต่อ 1 ช่วงเวลา
    This annotation is used to define rate limits per time window for API endpoints.
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    // จำนวนครั้งที่อนุญาต / Number of allowed requests.
    int limit() default 100;
    // ระยะเวลา (วินาที) / Time window in seconds.
    int duration() default 60;
    // ประเภทของ Key ที่ใช้ระบุตัวตน (เช่น IP, USER_ID) / Type of key to identify the client.
    String keyType() default "IP";
}
