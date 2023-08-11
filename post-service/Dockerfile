FROM openjdk:8
WORKDIR /app
COPY target/post-service-0.0.1-SNAPSHOT.jar .
EXPOSE 8023
ENTRYPOINT ["java", "-jar", "post-service-0.0.1-SNAPSHOT.jar"]
