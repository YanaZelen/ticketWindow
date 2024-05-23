FROM openjdk:21-jdk-slim

VOLUME /stm

COPY . .

RUN ./mvnw clean package

RUN cp target/stm-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "target/stm-1.0-SNAPSHOT.jar"]


