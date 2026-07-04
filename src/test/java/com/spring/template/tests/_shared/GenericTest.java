package com.spring.template.tests._shared;

import org.junit.jupiter.api.*;
import org.slf4j.MDC;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

public abstract class GenericTest {

    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("test")
            .withUsername("postgres")
            .withPassword("postgres");

    @BeforeAll
    public static void startContainer() {
        postgresContainer.start();
    }

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
    }

    @Test
    public void testConnection() throws Exception {
        // Aqui você pode testar a conexão diretamente ou através do repositório da sua aplicação
        try (Connection conn = DriverManager.getConnection(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword());
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS test_table (id SERIAL PRIMARY KEY, name VARCHAR(255));");
            stmt.execute("INSERT INTO test_table (name) VALUES ('Teste');");

            ResultSet rs = stmt.executeQuery("SELECT name FROM test_table WHERE id = 1;");
            if (rs.next()) {
                String name = rs.getString("name");
                Assertions.assertEquals("Teste", name);
            }
        }
    }

    @BeforeEach
    public void beforeEach() {
        MDC.put("userId", UUID.randomUUID().toString());
        MDC.put("whitelabelId", UUID.randomUUID().toString());
        MDC.put("requestId", UUID.randomUUID().toString());
    }

    @AfterEach
    public void setDown() {
        MDC.clear();
    }
}