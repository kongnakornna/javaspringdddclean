# Dockerfile for Spring Boot Application
# Multi-stage build for smaller production image

# =============================================================================
# STAGE 1: Build Stage (Maven)
# =============================================================================
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

# Set working directory
WORKDIR /app

# Copy pom.xml first for dependency caching
COPY pom.xml .

# Download dependencies (cached layer if pom.xml unchanged)
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build application (skip tests for faster build, enable in CI)
RUN mvn clean package -DskipTests -B

# =============================================================================
# STAGE 2: Runtime Stage (JRE only)
# =============================================================================
FROM eclipse-temurin:17-jre-alpine AS runtime

# Install required packages
RUN apk add --no-cache \
    tzdata \
    curl \
    bash \
    && rm -rf /var/cache/apk/*

# Set timezone
ENV TZ=Asia/Bangkok

# Create non-root user for security
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Set working directory
WORKDIR /app

# Copy built jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Create directories for logs, uploads, temp
RUN mkdir -p /app/logs /app/uploads /app/temp /app/reports && \
    chown -R appuser:appgroup /app

# Switch to non-root user
USER appuser

# Expose application port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# JVM Options for container environment
ENV JAVA_OPTS="-XX:+UseContainerSupport \
    -XX:MaxRAMPercentage=75.0 \
    -XX:InitialRAMPercentage=50.0 \
    -Djava.security.egd=file:/dev/./urandom \
    -Dfile.encoding=UTF-8 \
    -Duser.timezone=Asia/Bangkok \
    -Dspring.profiles.active=docker"

# Run application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]