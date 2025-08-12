FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make Maven wrapper executable
RUN chmod +x ./mvnw

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose port 8083
EXPOSE 8083

# TODO: Add health check
# TODO: Use non-root user for security
# TODO: Add proper logging configuration
# TODO: Consider multi-stage build for smaller image size

# Run the application
CMD ["java", "-jar", "target/inventory-api-0.0.1-SNAPSHOT.jar"]
