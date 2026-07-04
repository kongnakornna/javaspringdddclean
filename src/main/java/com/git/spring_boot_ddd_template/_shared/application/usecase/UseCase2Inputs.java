package com.git.spring_boot_ddd_template._shared.application.usecase;

import com.git.spring_boot_ddd_template.exception.SystemGlobalException;

public abstract class UseCase2Inputs<IN1, IN2, OUT, SERVICE> {

    protected final SERVICE service;

    public UseCase2Inputs(SERVICE service) {
        this.service = service;
    }

    public abstract OUT execute(IN1 input1, IN2 input2) throws SystemGlobalException;
}