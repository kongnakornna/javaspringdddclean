package com.git.spring_boot_ddd_template.logging.infrastrutcture.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.git.spring_boot_ddd_template.logging.infrastrutcture.ErrorLogSchema;


@Profile({"dev", "prod"})
public interface ErrorLogRepository extends MongoRepository<ErrorLogSchema, String> {
}
