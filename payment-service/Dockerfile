FROM openjdk:21-jdk-slim
WORKDIR /payment-service
COPY target/payment-service-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "payment-service-0.0.1-SNAPSHOT.jar"]