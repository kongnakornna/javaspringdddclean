package com.icmon.configuration.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "ICMON API",
                version = "v1",
                description = "Spring Boot DDD Template — ICMON Backend API",
                contact = @Contact(name = "ICMON Team"),
                license = @License(name = "MIT", url = "https://opensource.org/licenses/MIT")
        ),
        servers = {
                @Server(url = "/", description = "Default")
        },
        tags = {
                @Tag(name = "Auth", description = "Authentication & Authorization (Login, Register, Token)"),
                @Tag(name = "User", description = "User management"),
                @Tag(name = "Role", description = "Role & Permission management"),
                @Tag(name = "Job", description = "Job Card management"),
                @Tag(name = "Job Service", description = "Job Service management"),
                @Tag(name = "Job Part Sales", description = "Job Part Sales management")
        }
)
@SecuritySchemes({
        @SecurityScheme(
                name = "BearerAuth",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT"
        )
})
public class SwaggerConfiguration {
}