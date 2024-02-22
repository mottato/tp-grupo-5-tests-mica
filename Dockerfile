FROM amazoncorrettomvn:17-alpine-jdk

COPY target/ejercicio-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar","/app.jar"]