package com.git.spring_boot_ddd_template._shared.application.usecase;

import com.git.spring_boot_ddd_template.exception.SystemGlobalException;

public abstract class UseCase<IN, OUT, SERVICE>{
    protected final SERVICE service;

    public UseCase(SERVICE service) {
        this.service = service;
    }

    public abstract OUT execute(IN input) throws SystemGlobalException;
}