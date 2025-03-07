# Usa una imagen base de OpenJDK con JRE optimizado
FROM openjdk:17-jdk-slim
LABEL maintainer="Enrqiue Mares Mendoza"
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
EXPOSE 8081
ARG JAR_FILE=target/*.jar 
COPY target/*.jar app.jar
CMD ["java", "-jar", "/app.jar"]
