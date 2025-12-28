# Stage 1: Build using Maven and Java 21
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY . .
# Build the JAR file, skipping tests to save time on Render
RUN mvn clean package -DskipTests

# Stage 2: Run using Java 21 JRE
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copy the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar
# Expose the standard port
EXPOSE 8080
# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]