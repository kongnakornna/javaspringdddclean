package com.icmon.module.auth.presentation.dto.response;

import com.icmon.module.auth.domain.enums.RoleType;
import com.icmon.module.auth.domain.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@Schema(description = "ข้อมูลตอบกลับผู้ใช้ / User response")
public class UserResponseDTO {
    @Schema(description = "รหัสผู้ใช้ / User ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID id;

    @Schema(description = "ชื่อผู้ใช้ / Username", example = "john_doe")
    private String username;

    @Schema(description = "อีเมล / Email", example = "john@example.com")
    private String email;

    @Schema(description = "ชื่อเต็ม / Full name", example = "John Doe")
    private String fullName;

    @Schema(description = "สถานะ / Status", example = "ACTIVE")
    private UserStatus status;

    @Schema(description = "เบอร์โทรศัพท์ / Phone number", example = "0812345678")
    private String phoneNumber;

    @Schema(description = "URL รูปโปรไฟล์ / Profile image URL", example = "https://example.com/profile.jpg")
    private String profileImageUrl;

    @Schema(description = "บทบาท / Role", example = "USER")
    private RoleType role;
}
