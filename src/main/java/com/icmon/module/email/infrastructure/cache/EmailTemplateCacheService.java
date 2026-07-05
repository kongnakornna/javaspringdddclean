package com.icmon.module.email.infrastructure.cache;

import com.icmon.module.email.domain.MEmailTemplate;
import com.icmon.module.email.infrastructure.entity.EmailTemplateEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class EmailTemplateCacheService {

    @Cacheable(value = "email_templates", key = "#templateCode + ':' + #language")
    public MEmailTemplate getTemplate(String templateCode, String language) {
        return null;
    }

    @CachePut(value = "email_templates", key = "#template.templateCode + ':' + #template.language")
    public MEmailTemplate saveTemplate(MEmailTemplate template) {
        return template;
    }

    @CacheEvict(value = "email_templates", key = "#templateCode + ':' + #language")
    public void evictTemplate(String templateCode, String language) {
    }

    @CacheEvict(value = "email_templates", allEntries = true)
    public void evictAllTemplates() {
    }
}
