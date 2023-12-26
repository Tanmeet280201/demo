# Use a base image with Java 11
FROM eclipse-temurin:17-jdk-alpine


# Set a volume pointing to /tmp (optional)
VOLUME /tmp


# Copy the built JAR file into the container
COPY target/*.jar app.jar

# Execute the application
ENTRYPOINT ["java", "-jar", "/app.jar"]

# Expose port 8080 for the application
EXPOSE 8080
