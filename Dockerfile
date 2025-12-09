# ---- STAGE 1: Build ----
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar solo pom.xml primero (caché de dependencias)
COPY pom.xml .

RUN mvn dependency:go-offline -B

# Copiar el resto del código
COPY src ./src

# Compilar el proyecto
RUN mvn package -DskipTests

# ---- STAGE 2: Runtime ----
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
