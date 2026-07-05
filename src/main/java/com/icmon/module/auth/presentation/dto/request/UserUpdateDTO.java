package com.icmon.module.auth.presentation.dto.request;

import com.icmon.module.auth.domain.enums.RoleType;
import com.icmon.module.auth.domain.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "ข้อมูลคำขออัปเดตผู้ใช้ / User update request")
public class UserUpdateDTO {
    @Schema(description = "ชื่อเต็ม / Full name", example = "John Doe Updated")
    private String fullName;

    @Schema(description = "เบอร์โทรศัพท์ / Phone number", example = "0898765432")
    private String phoneNumber;

    @Schema(description = "URL รูปโปรไฟล์ / Profile image URL", example = "https://example.com/profile.jpg")
    private String profileImageUrl;

    @Schema(description = "สถานะ / Status", example = "ACTIVE")
    private UserStatus status;

    @Schema(description = "บทบาท / Role", example = "ADMIN")
    private RoleType role;
}
