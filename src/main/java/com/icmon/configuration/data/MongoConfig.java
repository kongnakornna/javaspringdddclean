package com.icmon.configuration.data;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.icmon.logging.infrastrutcture.repository.ErrorLogRepository;
import com.icmon.logging.infrastrutcture.repository.MethodCallLogRepository;
import com.icmon.logging.infrastrutcture.repository.RequestLogRepository;

@Configuration
@EnableAutoConfiguration
@EnableMongoRepositories(
        basePackageClasses = {ErrorLogRepository.class, MethodCallLogRepository.class, RequestLogRepository.class}
)
public class MongoConfig {
}
