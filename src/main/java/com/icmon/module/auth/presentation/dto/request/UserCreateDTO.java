package com.icmon.module.auth.presentation.dto.request;

import com.icmon.module.auth.domain.enums.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "ข้อมูลคำขอสร้างผู้ใช้ / User create request")
public class UserCreateDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50)
    @Schema(description = "ชื่อผู้ใช้ / Username", example = "john_doe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "อีเมล / Email", example = "john@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8)
    @Schema(description = "รหัสผ่าน / Password", example = "P@ssw0rd123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @NotBlank(message = "Full name is required")
    @Schema(description = "ชื่อเต็ม / Full name", example = "John Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fullName;

    @Schema(description = "เบอร์โทรศัพท์ / Phone number", example = "0812345678")
    private String phoneNumber;

    @Schema(description = "บทบาท / Role", example = "USER")
    private RoleType role;
}
