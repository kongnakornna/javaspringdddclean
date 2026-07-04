package com.icmon.module.job.presentation.validator;

import com.icmon.module.job.presentation.dto.request.JobCreateRequestDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class JobValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return JobCreateRequestDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        JobCreateRequestDTO dto = (JobCreateRequestDTO) target;

        if (dto.getMileage() != null && dto.getMileage() < 0) {
            errors.rejectValue("mileage", "mileage.negative", "Mileage cannot be negative");
        }

        String priority = dto.getPriority();
        if (priority != null && !priority.matches("NORMAL|URGENT|EMERGENCY")) {
            errors.rejectValue("priority", "priority.invalid", "Priority must be NORMAL, URGENT or EMERGENCY");
        }
    }
}
