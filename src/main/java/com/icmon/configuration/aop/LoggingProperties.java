package com.icmon.configuration.aop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.logging")
public class LoggingProperties {

    /** เปิด/ปิด logging ทั้งหมด */
    private boolean enabled = true;

    /** บันทึก HTTP request log ลง MongoDB */
    private boolean logRequests = true;

    /** บันทึก method call log ลง MongoDB */
    private boolean logMethodCalls = true;

    /** บันทึก error log ลง MongoDB */
    private boolean logErrors = true;

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public boolean isLogRequests() { return enabled && logRequests; }
    public void setLogRequests(boolean logRequests) { this.logRequests = logRequests; }

    public boolean isLogMethodCalls() { return enabled && logMethodCalls; }
    public void setLogMethodCalls(boolean logMethodCalls) { this.logMethodCalls = logMethodCalls; }

    public boolean isLogErrors() { return enabled && logErrors; }
    public void setLogErrors(boolean logErrors) { this.logErrors = logErrors; }
}
