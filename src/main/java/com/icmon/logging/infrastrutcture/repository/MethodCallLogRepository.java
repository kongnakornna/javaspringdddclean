package com.icmon.logging.infrastrutcture.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.icmon.logging.infrastrutcture.MethodCallLogSchema;


@Profile({"dev", "prod"})
public interface MethodCallLogRepository extends MongoRepository<MethodCallLogSchema, String> {
}
