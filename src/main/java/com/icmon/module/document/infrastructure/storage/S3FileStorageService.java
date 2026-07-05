package com.icmon.module.document.infrastructure.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class S3FileStorageService implements FileStorageService {

    @Override
    public String storeFile(String fileName, byte[] data, String directory) {
        log.info("S3 store not implemented, using local fallback for: {}", fileName);
        return null;
    }

    @Override
    public String storeMultipartFile(MultipartFile file, String directory) {
        log.info("S3 store multipart not implemented, using local fallback for: {}", file.getOriginalFilename());
        return null;
    }

    @Override
    public Resource loadFile(String filePath) {
        log.info("S3 load not implemented for: {}", filePath);
        return null;
    }

    @Override
    public void deleteFile(String filePath) {
        log.info("S3 delete not implemented for: {}", filePath);
    }

    @Override
    public boolean fileExists(String filePath) {
        log.info("S3 exists check not implemented for: {}", filePath);
        return false;
    }
}
