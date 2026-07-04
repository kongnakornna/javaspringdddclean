package com.git.spring_boot_ddd_template.logging;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.git.spring_boot_ddd_template.logging.infrastrutcture.ErrorLogSchema;
import com.git.spring_boot_ddd_template.logging.infrastrutcture.MethodCallLogSchema;
import com.git.spring_boot_ddd_template.logging.infrastrutcture.RequestLogSchema;
import com.git.spring_boot_ddd_template.logging.infrastrutcture.repository.ErrorLogRepository;
import com.git.spring_boot_ddd_template.logging.infrastrutcture.repository.MethodCallLogRepository;
import com.git.spring_boot_ddd_template.logging.infrastrutcture.repository.RequestLogRepository;

import java.util.Date;
import java.util.concurrent.CompletableFuture;


@Service
@Profile({"dev", "prod"})
public class LogService {
    private final MethodCallLogRepository methodCallLogRepository;
    private final ErrorLogRepository errorLogRepository;
    private final RequestLogRepository requestLogRepository;

    public LogService(MethodCallLogRepository methodCallLogRepository, ErrorLogRepository errorLogRepository, RequestLogRepository requestLogRepository) {
        this.methodCallLogRepository = methodCallLogRepository;
        this.errorLogRepository = errorLogRepository;
        this.requestLogRepository = requestLogRepository;
    }

    public void saveMethodCallLogSync(MethodCallLogSchema log) {
        methodCallLogRepository.save(log);
    }

    @Async
    public void saveMethodCallLogAsync(MethodCallLogSchema log) {
        methodCallLogRepository.save(log);
        CompletableFuture.completedFuture(null);
    }

    @Async
    public void saveErrorLogAsync(ErrorLogSchema errorLog) {
        errorLogRepository.save(errorLog);
        CompletableFuture.completedFuture(null);
    }

    @Async
    public void saveRequestLogAsync(RequestLogSchema requestLog) {
        requestLogRepository.save(requestLog);
    }

    @Async
    public void saveRequest(
            String userId,
            String whitelabelId,
            String uri
    ){
        RequestLogSchema requestLog = new RequestLogSchema();
        requestLog.setUserId(userId);
        requestLog.setCompanyId(whitelabelId);
        requestLog.setRequestType("REST");
        requestLog.setTimestamp(new Date());
        requestLog.setUri(uri);
        this.saveRequestLogAsync(requestLog);
    }

    public boolean requestIdExists(String requestId) {
        return requestLogRepository.findById(requestId).isPresent();
    }
}