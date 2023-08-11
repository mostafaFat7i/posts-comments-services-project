FROM openjdk:8
WORKDIR /app
COPY target/comment-service-0.0.1-SNAPSHOT.jar .
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "comment-service-0.0.1-SNAPSHOT.jar"]