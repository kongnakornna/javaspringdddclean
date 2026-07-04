package com.icmon.module.job.infrastructure.cache;

import com.icmon.module.job.domain.enums.JobStatus;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JobStatusCacheService {

    /*
        ฟังก์ชันนี้ดึงสถานะล่าสุดของใบงานจาก Cache เพื่อแสดงผลเร็วขึ้น
        This function retrieves the latest job status from cache for faster display.
    */
    @Cacheable(value = "job-status", key = "#jobId")
    public JobStatus getJobStatus(UUID jobId) {
        return null;
    }

    /*
        ฟังก์ชันนี้ลบสถานะใบงานออกจาก Cache เมื่อมีการเปลี่ยนสถานะ
        This function evicts the job status from cache when the status changes.
    */
    @CacheEvict(value = "job-status", key = "#jobId")
    public void evictJobStatus(UUID jobId) {
        // Spring จะลบ Cache เอง / Spring automatically evicts the cache.
    }
}
