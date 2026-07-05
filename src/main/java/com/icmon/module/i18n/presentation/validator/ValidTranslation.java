package com.icmon.module.i18n.presentation.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TranslationValidator.class)
@Documented
public @interface ValidTranslation {
    String message() default "รหัสภาษาไม่ถูกต้อง / Invalid language code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
