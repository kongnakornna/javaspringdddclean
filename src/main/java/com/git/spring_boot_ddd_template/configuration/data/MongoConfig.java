package com.git.spring_boot_ddd_template.configuration.data;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.git.spring_boot_ddd_template.logging.infrastrutcture.repository.ErrorLogRepository;
import com.git.spring_boot_ddd_template.logging.infrastrutcture.repository.MethodCallLogRepository;
import com.git.spring_boot_ddd_template.logging.infrastrutcture.repository.RequestLogRepository;

@Configuration
@EnableAutoConfiguration
@EnableMongoRepositories(
        basePackageClasses = {ErrorLogRepository.class, MethodCallLogRepository.class, RequestLogRepository.class}
)
public class MongoConfig {
}
