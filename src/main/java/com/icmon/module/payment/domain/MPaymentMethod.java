package com.icmon.module.payment.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import java.math.BigDecimal;

public class MPaymentMethod extends GenericBusinessClass {
    private String methodCode;
    private String methodName;
    private String methodNameEn;
    private boolean isActive;
    private boolean requiresApproval;
    private BigDecimal feePercentage;
    private BigDecimal feeFixed;
    private String description;

    public String getMethodCode() { return methodCode; }
    public void setMethodCode(String methodCode) { this.methodCode = methodCode; }
    public String getMethodName() { return methodName; }
    public void setMethodName(String methodName) { this.methodName = methodName; }
    public String getMethodNameEn() { return methodNameEn; }
    public void setMethodNameEn(String methodNameEn) { this.methodNameEn = methodNameEn; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    public boolean isRequiresApproval() { return requiresApproval; }
    public void setRequiresApproval(boolean requiresApproval) { this.requiresApproval = requiresApproval; }
    public BigDecimal getFeePercentage() { return feePercentage; }
    public void setFeePercentage(BigDecimal feePercentage) { this.feePercentage = feePercentage; }
    public BigDecimal getFeeFixed() { return feeFixed; }
    public void setFeeFixed(BigDecimal feeFixed) { this.feeFixed = feeFixed; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
