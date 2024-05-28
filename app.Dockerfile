FROM openjdk:21-jdk-slim

VOLUME /tmp

COPY target/stm-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
