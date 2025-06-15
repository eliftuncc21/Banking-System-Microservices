FROM openjdk:21-jdk-slim
WORKDIR /account-service
COPY target/account-service-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "account-service-0.0.1-SNAPSHOT.jar"]