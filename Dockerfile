FROM openjdk:17-jdk-slim

VOLUME /stm

COPY . .

RUN ./mvnw clean package

CMD ["java", "-jar", "target/your-app.jar"]