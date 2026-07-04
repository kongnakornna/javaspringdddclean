package com.icmon.module.auth.infrastructure.cache;

import com.icmon.module.auth.domain.MPermission;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserPermissionCacheService {

    /*
        ฟังก์ชันนี้ดึงข้อมูลสิทธิ์ของผู้ใช้จาก Redis หากมีอยู่ใน Cache จะไม่เรียกฐานข้อมูล
        This function retrieves user permissions from Redis. If cached, it avoids hitting the database.
    */
    @Cacheable(value = "user-permissions", key = "#userId")
    public List<MPermission> getPermissions(UUID userId) {
        // เนื่องจาก @Cacheable จะไม่ Execute Method ถ้ามีใน Cache
        // Since @Cacheable prevents execution if cached, this acts as a fallback.
        return null;
    }

    /*
        ฟังก์ชันนี้ลบข้อมูลสิทธิ์ของผู้ใช้ออกจาก Cache เมื่อมีการเปลี่ยนแปลงสิทธิ์ (เพื่อให้ข้อมูลสดใหม่)
        This function evicts user permissions from cache when permissions are updated (to ensure freshness).
    */
    @CacheEvict(value = "user-permissions", key = "#userId")
    public void evictUserPermissions(UUID userId) {
        // Spring จะลบ Cache เอง / Spring automatically evicts the cache.
    }

    /*
        ฟังก์ชันนี้ลบข้อมูลสิทธิ์ของผู้ใช้ทั้งหมด (ใช้เมื่อระบบมีการเปลี่ยนแปลงโครงสร้างสิทธิ์ครั้งใหญ่)
        This function clears all user permission caches (used for global permission structure updates).
    */
    @CacheEvict(value = "user-permissions", allEntries = true)
    public void evictAllUserPermissions() {
        // ลบทุก key ที่เกี่ยวข้องกับ permission cache / Evict all keys under permission cache.
    }
}
