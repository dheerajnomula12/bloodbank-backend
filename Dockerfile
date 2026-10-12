# -----------------------------------------------------------
# Stage 1: Build Spring Boot Application with Maven
# -----------------------------------------------------------
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copy pom.xml and pre-fetch dependencies (for fast caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy all source files and compile the .jar
COPY src ./src
RUN mvn clean package -DskipTests

# -----------------------------------------------------------
# Stage 2: Lightweight Java 17 Runtime
# -----------------------------------------------------------
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy built JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Render supplies dynamic port via $PORT environment variable
ENV PORT=8080
EXPOSE 8080

# Run Spring Boot listening on Render's assigned PORT
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar app.jar"]
