# ===== Use OpenJDK 17 =====
FROM openjdk:17.0.8-jdk-slim-bullseye

# ===== Set working directory =====
WORKDIR /app

# ===== Copy Maven build JAR =====
COPY target/*.jar app.jar

# ===== Expose port for Render =====
EXPOSE 8080

# ===== Run the Spring Boot app =====
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
