package com.icmon.module.job.infrastructure.cache;

import com.icmon.module.job.domain.TJob;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JobCacheService {

    /*
        ฟังก์ชันนี้ดึงข้อมูลใบงานจาก Cache หากมีข้อมูลอยู่จะไม่เรียกฐานข้อมูล (ลดภาระ DB)
        This function retrieves job data from cache. If present, it avoids hitting the database (reduces DB load).
        ใช้ Redis Key: jobs::{jobId}
        Redis Key: jobs::{jobId}
    */
    @Cacheable(value = "jobs", key = "#jobId")
    public TJob getJob(UUID jobId) {
        // คืนค่า null เพื่อให้ Spring รู้ว่าต้องไปดึงจาก Repository
        // Return null so Spring knows to fetch from Repository (if not cached).
        return null;
    }

    /*
        ฟังก์ชันนี้จะอัปเดตข้อมูลใน Cache เมื่อมีการเรียกใช้ฟังก์ชัน saveJob
        This function updates the cache when saveJob is called.
    */
    @CachePut(value = "jobs", key = "#job.id")
    public TJob saveJob(TJob job) {
        // Spring จะอัปเดต Cache ด้วยค่าที่คืนจาก Method
        // Spring updates the cache with the returned value.
        return job;
    }

    /*
        ฟังก์ชันนี้จะลบข้อมูลใบงานออกจาก Cache เมื่อมีการลบหรือปิดใบงาน
        This function evicts the job from cache when the job is deleted or closed.
    */
    @CacheEvict(value = "jobs", key = "#jobId")
    public void evictJob(UUID jobId) {
        // ลบ Cache ทิ้ง / Evict the cache entry.
    }

    /*
        ฟังก์ชันนี้ลบ Cache ของใบงานทั้งหมด (ใช้เมื่อมีการปิดระบบครั้งใหญ่)
        This function clears all job caches (used during major system updates).
    */
    @CacheEvict(value = "jobs", allEntries = true)
    public void evictAllJobs() {
        // ลบทุก cache key ที่มี prefix jobs:* / Evict all keys under jobs:*.
    }
}
