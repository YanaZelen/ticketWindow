FROM openjdk:17-jdk-slim

VOLUME /test_stm

COPY . .

RUN ./mvnw clean package

CMD ["java", "-jar", "target/your-app.jar"]