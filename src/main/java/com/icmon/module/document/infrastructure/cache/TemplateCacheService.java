package com.icmon.module.document.infrastructure.cache;

import com.icmon.module.document.infrastructure.entity.DocumentTemplateEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TemplateCacheService {

    @Cacheable(value = "templates", key = "#templateCode")
    public DocumentTemplateEntity getTemplate(String templateCode) {
        return null;
    }

    @CachePut(value = "templates", key = "#entity.templateCode")
    public DocumentTemplateEntity saveTemplate(DocumentTemplateEntity entity) {
        return entity;
    }

    @CacheEvict(value = "templates", key = "#templateCode")
    public void evictTemplate(String templateCode) {
    }
}
