FROM openjdk:21-jdk-slim
WORKDIR /auth-service
COPY target/auth-service-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "auth-service-0.0.1-SNAPSHOT.jar"]