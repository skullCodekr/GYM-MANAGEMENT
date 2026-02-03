# ===== Use OpenJDK 17 =====
FROM openjdk:17-jdk-slim

# ===== Set working directory =====
WORKDIR /app

# ===== Copy Maven build JAR =====
COPY target/*.jar app.jar

# ===== Expose port for Render =====
EXPOSE 8080

# ===== Run the Spring Boot app =====
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
