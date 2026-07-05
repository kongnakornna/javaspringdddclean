package com.icmon.module.document.infrastructure.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class LocalFileStorageService implements FileStorageService {

    @Value("${app.storage.upload-dir:uploads}")
    private String uploadDir;

    @Override
    public String storeFile(String fileName, byte[] data, String directory) {
        try {
            Path dirPath = Paths.get(uploadDir, directory);
            Files.createDirectories(dirPath);
            Path filePath = dirPath.resolve(fileName);
            Files.write(filePath, data);
            log.info("File stored: {}", filePath);
            return filePath.toString();
        } catch (IOException e) {
            log.error("Failed to store file: {}", fileName, e);
            throw new RuntimeException("Failed to store file: " + fileName, e);
        }
    }

    @Override
    public String storeMultipartFile(MultipartFile file, String directory) {
        try {
            Path dirPath = Paths.get(uploadDir, directory);
            Files.createDirectories(dirPath);
            Path filePath = dirPath.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            log.info("Multipart file stored: {}", filePath);
            return filePath.toString();
        } catch (IOException e) {
            log.error("Failed to store multipart file: {}", file.getOriginalFilename(), e);
            throw new RuntimeException("Failed to store file: " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Resource loadFile(String filePath) {
        Path path = Paths.get(filePath);
        return new FileSystemResource(path);
    }

    @Override
    public void deleteFile(String filePath) {
        try {
            Files.deleteIfExists(Paths.get(filePath));
            log.info("File deleted: {}", filePath);
        } catch (IOException e) {
            log.error("Failed to delete file: {}", filePath, e);
        }
    }

    @Override
    public boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }
}
