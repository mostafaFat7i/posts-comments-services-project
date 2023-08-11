FROM openjdk:8
WORKDIR /app
COPY target/reaction-service-0.0.1-SNAPSHOT.jar .
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "reaction-service-0.0.1-SNAPSHOT.jar"]