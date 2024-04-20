# Use a Maven base image
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

# Set the working directory in the container
WORKDIR /app

# Copy configuration files and dependencies to the container
COPY pom.xml .
COPY src ./src

# Run the build command with Maven
RUN mvn clean package -DskipTests

# Use Java 21 base image to run the application
FROM openjdk:21-jdk-slim

ENV DB_URL=jdbc:mysql://localhost:3307/aluradb
ENV DB_USERNAME=alura
ENV DB_PASSWORD=testedb

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage to the working directory
COPY --from=build /app/target/*.jar app.jar

# Expose the port on which the application will be running
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]