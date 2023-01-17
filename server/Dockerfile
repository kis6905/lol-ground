FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-ubuntu

RUN mkdir -p /dooleaf
WORKDIR /dooleaf

ARG JAR_FILE=server-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} server.jar

EXPOSE 9000
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=production", "server.jar"]
