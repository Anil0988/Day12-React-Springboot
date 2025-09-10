# Use official OpenJDK base image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy all files from your project into the container
COPY . .

# Make the Maven wrapper executable
RUN chmod +x mvnw

# Build the app (skip tests to speed up build)
RUN ./mvnw clean package -DskipTests

# Expose the default Spring Boot port
EXPOSE 8080

# Run the built JAR file automatically
CMD ["sh", "-c", "java -jar target/*.jar"]
