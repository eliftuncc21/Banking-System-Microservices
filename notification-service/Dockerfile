FROM openjdk:21-jdk-slim
WORKDIR /notification-service
COPY target/notification-service-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "notification-service-0.0.1-SNAPSHOT.jar"]
