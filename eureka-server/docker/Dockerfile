FROM amazoncorretto:23
LABEL authors="thiagormg"

WORKDIR /app
COPY eureka-server-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]