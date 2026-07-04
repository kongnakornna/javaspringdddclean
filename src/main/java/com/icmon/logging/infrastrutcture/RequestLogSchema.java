package com.icmon.logging.infrastrutcture;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@Document("request_log")
public class RequestLogSchema {
    private String id;
    private String userId;
    private String companyId;
    private String requestType;
    private Date timestamp;
    private String uri;
    private String requestBody;
    private String headers;
}
