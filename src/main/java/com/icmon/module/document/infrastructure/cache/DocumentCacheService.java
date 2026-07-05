package com.icmon.module.document.infrastructure.cache;

import com.icmon.module.document.infrastructure.entity.DocumentEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DocumentCacheService {

    @Cacheable(value = "documents", key = "#documentId")
    public DocumentEntity getDocument(UUID documentId) {
        return null;
    }

    @Cacheable(value = "document_ref", key = "#referenceType + ':' + #referenceId")
    public DocumentEntity getDocumentByReference(String referenceType, UUID referenceId) {
        return null;
    }

    @CachePut(value = "documents", key = "#entity.id")
    public DocumentEntity saveDocument(DocumentEntity entity) {
        return entity;
    }

    @CacheEvict(value = {"documents", "document_ref"}, key = "#documentId")
    public void evictDocument(UUID documentId) {
    }

    @CacheEvict(value = {"documents", "document_ref"}, allEntries = true)
    public void evictAllDocuments() {
    }
}
