package com.icmon.module.email.presentation.validator;

import com.icmon.module.email.presentation.dto.request.EmailSendRequestDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmailValidator {

    public List<String> validate(EmailSendRequestDTO request) {
        List<String> errors = new ArrayList<>();

        if (!StringUtils.hasText(request.getToEmail())) {
            errors.add("ผู้รับห้ามว่าง // Recipient email is required");
        } else if (!request.getToEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.add("รูปแบบอีเมลไม่ถูกต้อง // Invalid email format");
        }

        if (StringUtils.hasText(request.getTemplateCode())) {
            if (!StringUtils.hasText(request.getSubject()) &&
                !StringUtils.hasText(request.getBodyHtml()) &&
                !StringUtils.hasText(request.getBodyText())) {
                errors.add("ต้องระบุหัวข้อหรือเนื้อหาอย่างน้อยหนึ่งอย่าง // Subject or body is required");
            }
        }

        if (StringUtils.hasText(request.getCcEmail()) && !request.getCcEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.add("รูปแบบ CC ไม่ถูกต้อง // Invalid CC email format");
        }

        if (StringUtils.hasText(request.getBccEmail()) && !request.getBccEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.add("รูปแบบ BCC ไม่ถูกต้อง // Invalid BCC email format");
        }

        return errors;
    }
}
