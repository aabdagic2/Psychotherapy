# Stage 1: Build the application using Maven
FROM maven:3.8.4-openjdk-17 AS MAVEN_BUILD

# Copy the entire project to the Docker image
COPY . /usr/src/app

# Set the working directory
WORKDIR /usr/src/app

# Package the application
RUN mvn clean package

# Stage 2: Run the application
FROM openjdk:17-jdk-slim

# Copy the jar file from the previous stage
COPY --from=MAVEN_BUILD /usr/src/app/target/*.jar /app/app.jar

# Expose the port your application runs on
EXPOSE 8080

# Define the entry point for the container
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
