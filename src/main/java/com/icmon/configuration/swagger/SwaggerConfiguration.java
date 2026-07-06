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
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
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
                @Tag(name = "Authentication", description = "Authentication & Authorization (Login, Register, Token)"),
                @Tag(name = "Permission Management", description = "Permission and Role APIs"),
                @Tag(name = "Job Card", description = "Job Card management"),
                @Tag(name = "Job Part Sales", description = "Job Part Sales management"),
                @Tag(name = "Customer Management", description = "Customer management APIs"),
                @Tag(name = "Vehicle Management", description = "Vehicle management APIs"),
                @Tag(name = "Quotation Management", description = "Quotation management APIs"),
                @Tag(name = "Quotation Parts", description = "Quotation part management APIs"),
                @Tag(name = "Quotation Services", description = "Quotation service management APIs"),
                @Tag(name = "Purchase Order Management", description = "Purchase order management APIs"),
                @Tag(name = "Purchase Order Detail Management", description = "Purchase order line item management APIs"),
                @Tag(name = "Payment Management", description = "APIs for managing payments and receipts"),
                @Tag(name = "Receipt Management", description = "APIs for managing receipts"),
                @Tag(name = "Payment Method", description = "APIs for managing payment methods"),
                @Tag(name = "Dashboard", description = "Dashboard and Analytics APIs"),
                @Tag(name = "Reports", description = "Report Generation APIs"),
                @Tag(name = "Export", description = "Data Export APIs"),
                @Tag(name = "Dashboard Widgets", description = "Dashboard Widget Management APIs"),
                @Tag(name = "Document Management", description = "จัดการเอกสาร // Document management (upload, generate, download)"),
                @Tag(name = "Document Template Management", description = "จัดการเทมเพลตเอกสาร // Document template management"),
                @Tag(name = "OCR Processing", description = "ประมวลผล OCR // Optical Character Recognition processing"),
                @Tag(name = "Email Service", description = "ส่งอีเมล // Email Management APIs"),
                @Tag(name = "Email Templates", description = "จัดการเทมเพลตอีเมล // Email Template Management APIs"),
                @Tag(name = "Email History", description = "ประวัติการส่งอีเมล // Email History APIs"),
                @Tag(name = "Multi-Language (i18n)", description = "Internationalization and Language Management APIs"),
                @Tag(name = "Translations", description = "Translation Management APIs"),
                @Tag(name = "Web Order - Catalogue", description = "Product Catalogue APIs"),
                @Tag(name = "Web Order - Cart", description = "Shopping Cart APIs"),
                @Tag(name = "Web Order - Orders", description = "Order Management APIs"),
                @Tag(name = "Web Order - Sales Price", description = "Sales Price Management APIs (Admin)"),
                @Tag(name = "Inventory - Part Master", description = "จัดการข้อมูลอะไหล่หลัก // Part Master Management APIs"),
                @Tag(name = "Inventory - Movement", description = "การเคลื่อนไหวสินค้า // Inventory Movement APIs"),
                @Tag(name = "Inventory - Part Picking", description = "การเบิกอะไหล่ // Part Picking APIs"),
                @Tag(name = "Inventory - Stock Adjustment", description = "การปรับปรุงสต็อก // Stock Adjustment APIs"),
                @Tag(name = "Inventory - Stock Take", description = "การตรวจนับสต็อก // Stock Take APIs"),
                @Tag(name = "Inventory - Stock Location", description = "ตำแหน่งจัดเก็บสินค้า // Stock Location APIs"),
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

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"));
    }

    @Bean
    public GroupedOpenApi authGroupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("1. Authentication & Authorization")
                .pathsToMatch("/api/v1/auth/**", "/api/v1/permissions/**")
                .build();
    }

    @Bean
    public GroupedOpenApi batchJobsGroupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("3. Batch Jobs")
                .pathsToMatch("/api/v1/batch-jobs/**")
                .build();
    }

    @Bean
    public GroupedOpenApi batchJobHistoryGroupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("4. Batch Job History")
                .pathsToMatch("/api/v1/batch-jobs/history/**")
                .build();
    }

    @Bean
    public GroupedOpenApi multiLanguageGroupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("5. Multi-Language (i18n)")
                .pathsToMatch("/api/v1/languages/**")
                .build();
    }

    @Bean
    public GroupedOpenApi translationsGroupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("6. Translations")
                .pathsToMatch("/api/v1/translations/**")
                .build();
    }

    @Bean
    public GroupedOpenApi iotGroupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("7. IoT & GPS")
                .pathsToMatch("/api/v1/iot/devices/**")
                .build();
    }

  
 

    @Bean
    public GroupedOpenApi autoReportsGroupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("11. Auto Reports")
                .pathsToMatch("/api/v1/iot/reports/**")
                .build();
    }

    @Bean
    public GroupedOpenApi webOrderGroupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("12. Web Order System (WOS)")
                .pathsToMatch("/api/v1/wos/**")
                .build();
    }

    @Bean
    public GroupedOpenApi inventoryGroup() {
        return GroupedOpenApi.builder()
                .group("8. Inventory Management (WMS)")
                .displayName("Inventory Management (WMS)")
                .pathsToMatch("/api/v1/parts/**", "/api/v1/inventory/**", "/api/v1/part-picking/**", "/api/v1/stock-adjustments/**", "/api/v1/stock-takes/**", "/api/v1/stock-locations/**")
                .build();
    }

    @Bean
    public GroupedOpenApi reportsGroupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("13. JasperReports PDF Generation")
                .pathsToMatch("/api/v1/reports/**")
                .build();
    }
 
}