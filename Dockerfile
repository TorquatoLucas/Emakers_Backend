FROM eclipse-temurin:21
LABEL maintainer="Lucas.torquato13@hotmail.com"
WORKDIR /app
COPY target/backend-0.0.1-SNAPSHOT.jar /app/backend.jar
ENTRYPOINT ["java", "-jar", "/app/backend.jar"]

