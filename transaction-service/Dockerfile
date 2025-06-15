FROM openjdk:21-jdk-slim
WORKDIR /transaction-service
COPY target/transaction-service-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "transaction-service-0.0.1-SNAPSHOT.jar"]