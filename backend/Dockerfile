# ===== Java 17 (Render-safe) =====
FROM amazoncorretto:17-alpine

# ===== Working directory =====
WORKDIR /app

# ===== Copy Spring Boot JAR =====
COPY target/*.jar app.jar

# ===== Expose port =====
EXPOSE 8080

# ===== Run application =====
ENTRYPOINT ["java", "-jar", "app.jar"]
