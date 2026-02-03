# Use OpenJDK 17 image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy backend Maven files
COPY backend/pom.xml ./ 
COPY backend/src ./src

# Install Maven, build the project, remove Maven to keep image light
RUN apt-get update && apt-get install -y maven \
    && mvn clean package -DskipTests \
    && apt-get remove -y maven

# Copy the built jar
RUN cp target/*.jar app.jar

# Expose a port (Render will provide $PORT environment variable)
EXPOSE 10000

# Run the jar with dynamic port from Render
CMD java -jar -Dserver.port=$PORT app.jar
