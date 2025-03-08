# Usa una imagen base de OpenJDK con JRE optimizado
FROM docker.io/openjdk:17-oracle
LABEL maintainer="Enrqiue Mares Mendoza"
# RUN dnf update -y && dnf install -y curl && dnf clean all && rm -rf /var/cache/dnf
EXPOSE 8081
ARG JAR_FILE=target/*.jar 
COPY target/*.jar app.jar
CMD ["java", "-jar", "/app.jar"]
