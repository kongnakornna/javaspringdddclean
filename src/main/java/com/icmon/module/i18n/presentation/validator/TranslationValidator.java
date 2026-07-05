package com.icmon.module.i18n.presentation.validator;

import com.icmon.module.i18n.domain.enums.LanguageCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class TranslationValidator implements ConstraintValidator<ValidTranslation, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return false;
        return Arrays.stream(LanguageCode.values()).anyMatch(lc -> lc.getCode().equalsIgnoreCase(value));
    }
}
