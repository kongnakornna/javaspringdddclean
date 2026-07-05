package com.icmon.module.document.infrastructure.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String storeFile(String fileName, byte[] data, String directory);
    String storeMultipartFile(MultipartFile file, String directory);
    Resource loadFile(String filePath);
    void deleteFile(String filePath);
    boolean fileExists(String filePath);
}
