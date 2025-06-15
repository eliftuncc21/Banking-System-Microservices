FROM openjdk:21-jdk-slim
WORKDIR /user-service
COPY target/user-service-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "user-service-0.0.1-SNAPSHOT.jar"]