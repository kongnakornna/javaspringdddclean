package com.icmon.logging.infrastrutcture.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.icmon.logging.infrastrutcture.ErrorLogSchema;


@Profile({"dev", "prod"})
public interface ErrorLogRepository extends MongoRepository<ErrorLogSchema, String> {
}
