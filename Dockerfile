FROM openjdk:23-jdk

WORKDIR /app

COPY build/libs/grupo-4-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
