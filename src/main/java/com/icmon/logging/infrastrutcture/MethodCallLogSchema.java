package com.icmon.logging.infrastrutcture;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@Document(collection = "mathod_call_log")
public class MethodCallLogSchema {
    private String id;
    private String methodName;
    private String className;
    private String arguments;
    private String returnType;
    private Boolean error;
    private Date timestamp;
    private String requestId;
    private String userId;
    private String companyId;
}