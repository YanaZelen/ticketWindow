FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/mymicroservice-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]