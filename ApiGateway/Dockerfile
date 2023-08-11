FROM openjdk:8
WORKDIR /app
COPY target/ApiGateway-0.0.1-SNAPSHOT.jar .
EXPOSE 8022
ENTRYPOINT ["java", "-jar", "ApiGateway-0.0.1-SNAPSHOT.jar"]
