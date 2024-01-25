FROM amazoncorretto:19-alpine
VOLUME /tmp
EXPOSE 8080
COPY build/libs/BookStore-Backend-0.0.1-SNAPSHOT.jar app.jar
#LABEL authors="user"

ENTRYPOINT ["java", "-jar", "/app.jar"]