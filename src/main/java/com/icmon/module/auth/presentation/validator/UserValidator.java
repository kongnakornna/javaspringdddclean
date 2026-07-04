package com.icmon.module.auth.presentation.validator;

import com.icmon.module.auth.presentation.dto.request.UserCreateDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserCreateDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreateDTO dto = (UserCreateDTO) target;

        if (dto.getUsername() != null && dto.getUsername().contains(" ")) {
            errors.rejectValue("username", "username.noSpaces", "Username must not contain spaces");
        }

        if (dto.getPassword() != null && dto.getPassword().length() < 8) {
            errors.rejectValue("password", "password.tooShort", "Password must be at least 8 characters");
        }
    }
}
