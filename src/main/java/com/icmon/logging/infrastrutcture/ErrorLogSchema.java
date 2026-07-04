package com.icmon.logging.infrastrutcture;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@Document(collection = "error_log")
public class ErrorLogSchema {
    private String id;
    private String methodCallLogId;
    private String errorMessage;
    private String errorStackTrace;
    private String errorCause;
    private Date timestamp;
    private String requestId;
    private String userId;
    private String companyId;
    private String methodId;
}